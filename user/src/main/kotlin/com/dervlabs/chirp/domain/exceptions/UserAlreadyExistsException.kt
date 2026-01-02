package com.dervlabs.chirp.domain.exceptions

class UserAlreadyExistsException : RuntimeException("User with same username already exists")