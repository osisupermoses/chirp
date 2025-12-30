package com.dervlabs.chirp.domain.events

import com.dervlabs.chirp.domain.types.UserId

data class ProfilePictureUpdatedEvent(
    val userId: UserId,
    val newUrl: String?
)
