package com.dervlabs.chirp.infra.security

interface PasswordHasher {
    fun encode(rawPassword: String): String
    fun matches(rawPassword: String, encodedPassword: String): Boolean
}