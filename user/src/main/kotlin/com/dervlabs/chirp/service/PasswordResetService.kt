package com.dervlabs.chirp.service

import com.dervlabs.chirp.domain.exception.InvalidCredentialsException
import com.dervlabs.chirp.domain.exception.InvalidTokenException
import com.dervlabs.chirp.domain.exception.SamePasswordException
import com.dervlabs.chirp.domain.exception.UserNotFoundException
import com.dervlabs.chirp.domain.type.UserId
import com.dervlabs.chirp.infra.database.entities.PasswordResetTokenEntity
import com.dervlabs.chirp.infra.database.repositories.PasswordResetTokenRepository
import com.dervlabs.chirp.infra.database.repositories.RefreshTokenRepository
import com.dervlabs.chirp.infra.database.repositories.UserRepository
import com.dervlabs.chirp.infra.security.PasswordHasher
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class PasswordResetService(
    private val userRepository: UserRepository,
    private val passwordResetTokenRepository: PasswordResetTokenRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val passwordHasher: PasswordHasher,
    @param:Value($$"${chirp.email.reset-password.expiry-minutes}") private val expiryMinutes: Long
) {

    @Transactional
    fun requestPasswordReset(email: String) {
        val user = userRepository.findByEmail(email) ?: return

        passwordResetTokenRepository.invalidateActiveTokensForUser(user)

        val token = PasswordResetTokenEntity(
            user = user,
            expiresAt = Instant.now().plus(expiryMinutes, ChronoUnit.MINUTES),
        )
        passwordResetTokenRepository.save(token)

        // TODO: Inform notification service about password reset trigger to send email
    }

    @Transactional
    fun resetPassword(token: String, newPassword: String) {
        val resetToken = passwordResetTokenRepository.findByToken(token)
            ?: throw InvalidTokenException("Invalid password reset token")

        if (resetToken.isUsed) {
            throw InvalidTokenException("Password reset token is already used")
        }

        if (resetToken.isExpired) {
            throw InvalidTokenException("Password reset token has already expired")
        }

        val user = resetToken.user

        if (passwordHasher.matches(newPassword, user.hashedPassword)) {
            throw SamePasswordException()
        }

        val hashedNewPassword = passwordHasher.encode(newPassword)
        userRepository.save(
            user.apply {
                this.hashedPassword = hashedNewPassword
            }
        )

        passwordResetTokenRepository.save(
            resetToken.apply {
                this.usedAt = Instant.now()
            }
        )

        refreshTokenRepository.deleteByUserId(user.id!!)
    }

    @Transactional
    fun changePassword(
        userId: UserId,
        oldPassword: String,
        newPassword: String,
    ) {
        val user = userRepository.findByIdOrNull(userId)
            ?: throw UserNotFoundException()

        if (!passwordHasher.matches(oldPassword, user.hashedPassword)) {
            throw InvalidCredentialsException()
        }

        if (oldPassword == newPassword) {
            throw SamePasswordException()
        }

        refreshTokenRepository.deleteByUserId(user.id!!)

        val newHashedPassword = passwordHasher.encode(newPassword)
        userRepository.save(
            user.apply {
                this.hashedPassword = newHashedPassword
            }
        )
    }

    @Scheduled(cron = "0 0 3 * * *")
    fun cleanupExpiredTokens() {
        passwordResetTokenRepository.deleteByExpiresAtLessThan(
            now = Instant.now()
        )
    }
}