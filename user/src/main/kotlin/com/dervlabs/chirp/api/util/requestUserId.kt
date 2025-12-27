package com.dervlabs.chirp.api.util

import com.dervlabs.chirp.domain.exception.UnauthorizedException
import com.dervlabs.chirp.domain.model.UserId
import org.springframework.security.core.context.SecurityContextHolder

val requestUserId: UserId
    get() = SecurityContextHolder.getContext().authentication?.principal as? UserId
        ?: throw UnauthorizedException()