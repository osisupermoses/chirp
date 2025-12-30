package com.dervlabs.chirp.api.dto

import com.dervlabs.chirp.api.utils.Password
import jakarta.validation.constraints.NotBlank

data class ResetPasswordRequest(
    @field:NotBlank
    val token: String,
    @field:Password
    val newPassword: String,
)