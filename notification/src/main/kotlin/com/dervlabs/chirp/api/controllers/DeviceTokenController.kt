package com.dervlabs.chirp.api.controllers

import com.dervlabs.chirp.api.dto.DeviceTokenDto
import com.dervlabs.chirp.api.dto.RegisterDeviceRequest
import com.dervlabs.chirp.api.mappers.toDeviceTokenDto
import com.dervlabs.chirp.api.mappers.toPlatform
import com.dervlabs.chirp.api.utils.requestUserId
import com.dervlabs.chirp.service.PushNotificationService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/notification")
class DeviceTokenController(
    private val pushNotificationService: PushNotificationService
) {

    @PostMapping("/register")
    fun registerDeviceToken(
        @Valid @RequestBody body: RegisterDeviceRequest
    ): DeviceTokenDto {
        return pushNotificationService.registerDevice(
            userId = requestUserId,
            token = body.token,
            platform = body.platform.toPlatform()
        ).toDeviceTokenDto()
    }

    @DeleteMapping("/{token}")
    fun unregisterDeviceToken(
        @PathVariable("token") token: String
    ) {
        pushNotificationService.unregisterDevice(token)
    }
}