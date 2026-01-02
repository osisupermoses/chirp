package com.dervlabs.chirp.infra.mappers

import com.dervlabs.chirp.domain.models.DeviceToken
import com.dervlabs.chirp.infra.database.DeviceTokenEntity

fun DeviceTokenEntity.toDeviceToken(): DeviceToken {
    return DeviceToken(
        id = id,
        userId = userId,
        token = token,
        platform = platform.toPlatform(),
        createdAt = createdAt,
    )
}