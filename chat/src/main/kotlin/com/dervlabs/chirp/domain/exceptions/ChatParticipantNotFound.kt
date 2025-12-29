package com.dervlabs.chirp.domain.exceptions

import com.dervlabs.chirp.domain.type.UserId

class ChatParticipantNotFound(
    private val id: UserId
) : RuntimeException(
    "The chat participant with the ID $id was not found."
)