package com.dervlabs.chirp.api.mappers

import com.dervlabs.chirp.api.dto.DeviceTokenDto
import com.dervlabs.chirp.api.dto.PlatformDto
import com.dervlabs.chirp.domain.models.DeviceToken

fun DeviceToken.toDeviceTokenDto(): DeviceTokenDto {
    return DeviceTokenDto(
        userId = userId,
        token = token,
        createdAt = createdAt
    )
}

fun PlatformDto.toPlatform(): DeviceToken.Platform {
    return when(this) {
        PlatformDto.ANDROID -> DeviceToken.Platform.ANDROID
        PlatformDto.IOS -> DeviceToken.Platform.IOS
    }
}