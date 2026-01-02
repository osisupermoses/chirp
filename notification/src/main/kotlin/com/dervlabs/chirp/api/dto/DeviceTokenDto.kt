package com.dervlabs.chirp.api.dto

import com.dervlabs.chirp.domain.types.UserId
import java.time.Instant

data class DeviceTokenDto(
    val userId: UserId,
    val token: String,
    val createdAt: Instant
)