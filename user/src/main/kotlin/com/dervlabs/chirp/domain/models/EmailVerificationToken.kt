package com.dervlabs.chirp.domain.models

data class EmailVerificationToken(
    val id: Long,
    val token: String,
    val user: User
)
