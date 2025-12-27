package com.dervlabs.chirp.domain.exception

class RateLimitException(
    val resetsInSeconds: Long
) : RuntimeException(
    "Rate limit exceeded, please try again in $resetsInSeconds seconds."
)