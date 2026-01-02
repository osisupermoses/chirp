package com.dervlabs.chirp.api.dto.ws

import com.dervlabs.chirp.domain.types.ChatId
import com.dervlabs.chirp.domain.types.ChatMessageId

data class DeleteMessageDto(
    val chatId: ChatId,
    val messageId: ChatMessageId
)
