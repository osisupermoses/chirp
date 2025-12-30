package com.dervlabs.chirp.domain.events

import com.dervlabs.chirp.domain.type.ChatId
import com.dervlabs.chirp.domain.type.UserId

data class ChatParticipantJoinedEvent(
    val chatId: ChatId,
    val userIds: Set<UserId>
)
