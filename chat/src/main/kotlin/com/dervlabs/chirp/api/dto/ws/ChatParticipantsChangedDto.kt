package com.dervlabs.chirp.api.dto.ws

import com.dervlabs.chirp.domain.types.ChatId

data class ChatParticipantsChangedDto(
    val chatId: ChatId
)
