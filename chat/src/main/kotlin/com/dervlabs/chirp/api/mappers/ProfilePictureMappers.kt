package com.dervlabs.chirp.api.mappers

import com.dervlabs.chirp.api.dto.PictureUploadResponse
import com.dervlabs.chirp.domain.models.ProfilePictureUploadCredentials

fun ProfilePictureUploadCredentials.toResponse(): PictureUploadResponse {
    return PictureUploadResponse(
        uploadUrl = uploadUrl,
        publicUrl = publicUrl,
        headers = headers,
        expiresAt = expiresAt
    )
}