package com.dervlabs.chirp.infra.database.mappers

import com.dervlabs.chirp.domain.model.EmailVerificationToken
import com.dervlabs.chirp.infra.database.entities.EmailVerificationTokenEntity

fun EmailVerificationTokenEntity.toEmailVerificationToken(): EmailVerificationToken {
    return EmailVerificationToken(
        id = id,
        token = token,
        user = user.toUser()
    )
}