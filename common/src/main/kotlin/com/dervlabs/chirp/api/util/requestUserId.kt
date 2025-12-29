package com.dervlabs.chirp.api.util

import com.dervlabs.chirp.domain.exceptions.UnauthorizedException
import com.dervlabs.chirp.domain.type.UserId
import org.springframework.security.core.context.SecurityContextHolder

val requestUserId: UserId
    get() = SecurityContextHolder.getContext().authentication?.principal as? UserId
        ?: throw UnauthorizedException()