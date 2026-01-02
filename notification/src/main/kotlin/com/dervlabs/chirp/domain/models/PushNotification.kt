package com.dervlabs.chirp.domain.models

import com.dervlabs.chirp.domain.types.ChatId
import java.util.UUID

data class PushNotification(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val recipients: List<DeviceToken>,
    val message: String,
    val chatId: ChatId,
    val data: Map<String, String>
)