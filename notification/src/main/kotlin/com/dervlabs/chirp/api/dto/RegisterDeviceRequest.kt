package com.dervlabs.chirp.api.dto

import jakarta.validation.constraints.NotBlank

data class RegisterDeviceRequest(
    @field:NotBlank(message = "token cannot be blank")
    val token: String,
    val platform: PlatformDto
)

enum class PlatformDto {
    ANDROID, IOS
}
