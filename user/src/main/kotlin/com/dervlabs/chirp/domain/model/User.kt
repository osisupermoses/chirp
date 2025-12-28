package com.dervlabs.chirp.domain.model

import com.dervlabs.chirp.domain.type.UserId

data class User(
    val id: UserId,
    val username: String,
    val email: String,
    val hasEmailVerified: Boolean,
)
