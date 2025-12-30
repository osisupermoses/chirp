package com.dervlabs.chirp.domain.events

import com.dervlabs.chirp.domain.type.ChatId
import com.dervlabs.chirp.domain.type.UserId

data class ChatParticipantLeftEvent(
    val chatId: ChatId,
    val userId: UserId
)
