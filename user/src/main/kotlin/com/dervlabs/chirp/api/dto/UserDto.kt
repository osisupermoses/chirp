package com.dervlabs.chirp.api.dto

import com.dervlabs.chirp.domain.types.UserId

data class UserDto(
    val id: UserId,
    val email: String,
    val username: String,
    val hasVerifiedEmail: Boolean,
)
