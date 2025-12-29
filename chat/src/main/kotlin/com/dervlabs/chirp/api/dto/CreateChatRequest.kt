package com.dervlabs.chirp.api.dto

import com.dervlabs.chirp.domain.type.UserId
import jakarta.validation.constraints.Size

data class CreateChatRequest(
    @field:Size(
        min = 1,
        message = "Chats must have at least to 2 unique participants",
    )
    val otherUserIds: List<UserId>
)
