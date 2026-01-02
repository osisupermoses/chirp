package com.dervlabs.chirp.infra.security

import org.springframework.stereotype.Component
import org.springframework.security.crypto.password.PasswordEncoder

@Component
class BcryptPasswordHasher(
    private val encoder: PasswordEncoder
) : PasswordHasher {

    override fun encode(rawPassword: String): String =
        encoder.encode(rawPassword) ?: error("Error while encrypting password")

    override fun matches(rawPassword: String, encodedPassword: String): Boolean =
        encoder.matches(rawPassword, encodedPassword)
}