package com.dervlabs.chirp.api.dto

import com.dervlabs.chirp.domain.types.ChatId
import com.dervlabs.chirp.domain.types.ChatMessageId
import com.dervlabs.chirp.domain.types.UserId
import java.time.Instant

data class ChatMessageDto(
    val id: ChatMessageId,
    val chatId: ChatId,
    val content: String,
    val createdAt: Instant,
    val senderId: UserId
)