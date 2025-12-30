package com.dervlabs.chirp.api.dto.ws

import com.dervlabs.chirp.domain.types.ChatId
import com.dervlabs.chirp.domain.types.ChatMessageId

data class SendMessageDto(
    val chatId: ChatId,
    val content: String,
    val messageId: ChatMessageId? = null
)
