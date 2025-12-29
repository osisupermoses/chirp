package com.dervlabs.chirp.api.dto

import com.dervlabs.chirp.domain.type.UserId
import jakarta.validation.constraints.Size

data class AddParticipantToChatDto(
    @field:Size(min = 1)
    val userIds: List<UserId>
)
