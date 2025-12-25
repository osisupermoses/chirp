package com.dervlabs.chirp.domain.exception

class UserAlreadyExistsException : RuntimeException("User with same username already exists")