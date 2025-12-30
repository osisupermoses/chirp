package com.dervlabs.chirp.api.dto

import com.dervlabs.chirp.api.utils.Password
import jakarta.validation.constraints.NotBlank

data class ChangePasswordRequest(
    @field:NotBlank
    val oldPassword: String,
    @field:Password
    val newPassword: String,
)