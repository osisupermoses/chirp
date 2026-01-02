package com.dervlabs.chirp.api.dto

import com.dervlabs.chirp.domain.types.UserId

data class ChatParticipantDto(
    val userId: UserId,
    val username: String,
    val email: String,
    val profilePictureUrl: String?
)
