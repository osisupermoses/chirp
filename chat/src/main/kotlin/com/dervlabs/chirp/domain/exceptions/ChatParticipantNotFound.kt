package com.dervlabs.chirp.domain.exceptions

import com.dervlabs.chirp.domain.type.UserId

class ChatParticipantNotFound(
    id: UserId
) : RuntimeException(
    "The chat participant with the ID $id was not found."
)