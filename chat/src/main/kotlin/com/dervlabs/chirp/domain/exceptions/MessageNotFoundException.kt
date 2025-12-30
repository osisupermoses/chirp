package com.dervlabs.chirp.domain.exceptions

import com.dervlabs.chirp.domain.types.ChatMessageId

class MessageNotFoundException(
    id: ChatMessageId
): RuntimeException("Message with ID $id not found")