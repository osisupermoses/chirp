package com.dervlabs.chirp.domain.events

import com.dervlabs.chirp.domain.types.ChatId
import com.dervlabs.chirp.domain.types.ChatMessageId

data class MessageDeletedEvent(
    val chatId: ChatId,
    val messageId: ChatMessageId
)
