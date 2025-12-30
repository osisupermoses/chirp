package com.dervlabs.chirp.domain.exceptions

class InvalidProfilePictureException(
    override val message: String? = null
) : RuntimeException(message ?: "Invalid profile picture data")