package com.dervlabs.chirp.api.controllers

import com.dervlabs.chirp.api.dto.AddParticipantToChatDto
import com.dervlabs.chirp.api.dto.ChatDto
import com.dervlabs.chirp.api.dto.CreateChatRequest
import com.dervlabs.chirp.api.mappers.toChatDto
import com.dervlabs.chirp.api.util.requestUserId
import com.dervlabs.chirp.domain.type.ChatId
import com.dervlabs.chirp.infra.service.ChatService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/chat")
class ChatController(
    private val chatService: ChatService,
) {

    @PostMapping
    fun createChat(
        @Valid @RequestBody body: CreateChatRequest
    ): ChatDto {
        return chatService.createChat(
            creatorId = requestUserId,
            otherUserIds = body.otherUserIds.toSet()
        ).toChatDto()
    }

    @PostMapping("/{chatId}/add")
    fun addChatParticipants(
        @PathVariable chatId: ChatId,
        @Valid @RequestBody body: AddParticipantToChatDto
    ): ChatDto {
        return chatService.addParticipantsToChat(
            requestUserId = requestUserId,
            chatId = chatId,
            userIds = body.userIds.toSet()
        ).toChatDto()
    }

    @DeleteMapping("/{chatId}/leave")
    fun leaveChat(
        @PathVariable chatId: ChatId
    ) {
        return chatService.removeParticipantFromChat(
            chatId = chatId,
            userId = requestUserId
        )
    }
}