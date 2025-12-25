package com.dervlabs.chirp.infra.database.mappers

import com.dervlabs.chirp.domain.model.User
import com.dervlabs.chirp.infra.database.entities.UserEntity

fun UserEntity.toUser() = User(
    id = id!!,
    username = username,
    email = email,
    hasEmailVerified = hasVerifiedEmail,
)