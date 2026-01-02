package com.dervlabs.chirp.domain.events

import com.dervlabs.chirp.domain.types.ChatId
import com.dervlabs.chirp.domain.types.UserId

data class ChatParticipantJoinedEvent(
    val chatId: ChatId,
    val userIds: Set<UserId>
)
