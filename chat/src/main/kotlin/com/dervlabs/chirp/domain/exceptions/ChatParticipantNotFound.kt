package com.dervlabs.chirp.domain.exceptions

import com.dervlabs.chirp.domain.types.UserId

class ChatParticipantNotFound(
    id: UserId
) : RuntimeException(
    "The chat participant with the ID $id was not found."
)