package com.dervlabs.chirp.domain.events

import com.dervlabs.chirp.domain.type.ChatId
import com.dervlabs.chirp.domain.type.ChatMessageId

data class MessageDeletedEvent(
    val chatId: ChatId,
    val messageId: ChatMessageId
)
