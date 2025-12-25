package com.dervlabs.chirp.api.mappers

import com.dervlabs.chirp.api.dto.AuthenticatedUserDto
import com.dervlabs.chirp.api.dto.UserDto
import com.dervlabs.chirp.domain.model.AuthenticatedUser
import com.dervlabs.chirp.domain.model.User

fun AuthenticatedUser.toAuthenticatedUserDto(): AuthenticatedUserDto {
    return AuthenticatedUserDto(
        user = user.toUserDto(),
        accessToken = accessToken,
        refreshToken = refreshToken,
    )
}

fun User.toUserDto(): UserDto {
    return UserDto(
        id = id,
        email = email,
        username = username,
        hasVerifiedEmail = hasEmailVerified,
    )
}