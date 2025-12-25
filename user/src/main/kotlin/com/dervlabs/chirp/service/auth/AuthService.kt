package com.dervlabs.chirp.service.auth

import com.dervlabs.chirp.domain.exception.UserAlreadyExistsException
import com.dervlabs.chirp.domain.model.User
import com.dervlabs.chirp.infra.database.entities.UserEntity
import com.dervlabs.chirp.infra.database.mappers.toUser
import com.dervlabs.chirp.infra.database.repositories.UserRepository
import com.dervlabs.chirp.infra.security.PasswordHasher
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordHasher: PasswordHasher
) {

    fun register(email: String, username: String, password: String): User {
        val user = userRepository.findByEmailOrUsername(
            email = email.trim(),
            username = username.trim()
        )
        if (user != null) {
            throw UserAlreadyExistsException()
        }

        val savedUser = userRepository.save(
            UserEntity(
                email = email.trim(),
                username = username.trim(),
                hashedPassword = passwordHasher.encode(password)
            )
        ).toUser()

        return savedUser
    }
}