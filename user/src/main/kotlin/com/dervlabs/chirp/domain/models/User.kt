package com.dervlabs.chirp.domain.models

import com.dervlabs.chirp.domain.types.UserId

data class User(
    val id: UserId,
    val username: String,
    val email: String,
    val hasEmailVerified: Boolean,
)
