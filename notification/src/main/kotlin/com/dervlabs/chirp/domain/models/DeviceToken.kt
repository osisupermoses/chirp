package com.dervlabs.chirp.domain.models

import com.dervlabs.chirp.domain.types.UserId
import java.time.Instant

data class DeviceToken(
    val id: Long,
    val userId: UserId,
    val token: String,
    val platform: Platform,
    val createdAt: Instant = Instant.now()
) {
    enum class Platform {
        ANDROID, IOS
    }
}
