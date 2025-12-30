package com.dervlabs.chirp.api.dto.ws

import com.dervlabs.chirp.domain.types.UserId

data class ProfilePictureUpdateDto(
    val userId: UserId,
    val newUrl: String?
)
