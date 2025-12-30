package com.dervlabs.chirp.service

import com.dervlabs.chirp.domain.events.MessageDeletedEvent
import com.dervlabs.chirp.domain.events.chat.ChatEvent
import com.dervlabs.chirp.domain.exceptions.ChatNotFoundException
import com.dervlabs.chirp.domain.exceptions.ChatParticipantNotFound
import com.dervlabs.chirp.domain.exceptions.ForbiddenException
import com.dervlabs.chirp.domain.exceptions.MessageNotFoundException
import com.dervlabs.chirp.domain.models.ChatMessage
import com.dervlabs.chirp.domain.types.ChatId
import com.dervlabs.chirp.domain.types.ChatMessageId
import com.dervlabs.chirp.domain.types.UserId
import com.dervlabs.chirp.infra.database.entities.ChatMessageEntity
import com.dervlabs.chirp.infra.database.mappers.toChatMessage
import com.dervlabs.chirp.infra.database.repositories.ChatMessageRepository
import com.dervlabs.chirp.infra.database.repositories.ChatParticipantRepository
import com.dervlabs.chirp.infra.database.repositories.ChatRepository
import com.dervlabs.chirp.infra.message_queue.EventPublisher
import org.springframework.cache.annotation.CacheEvict
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatMessageService(
    private val chatRepository: ChatRepository,
    private val chatMessageRepository: ChatMessageRepository,
    private val chatParticipantRepository: ChatParticipantRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val eventPublisher: EventPublisher
) {

    @Transactional
    @CacheEvict(
        value = [CHAT_MESSAGES_CACHE_NAME],
        key = "#chatId"
    )
    fun sendMessage(
        chatId: ChatId,
        senderId: UserId,
        content: String,
        messageId: ChatMessageId? = null
    ): ChatMessage {
        val chat = chatRepository.findChatById(chatId, senderId)
            ?: throw ChatNotFoundException()
        val sender = chatParticipantRepository.findByIdOrNull(senderId)
            ?: throw ChatParticipantNotFound(senderId)

        val savedMessage = chatMessageRepository.saveAndFlush(
            ChatMessageEntity(
                id = messageId,
                content = content,
                chatId = chatId,
                chat = chat,
                sender = sender
            )
        )

        eventPublisher.publish(
            event = ChatEvent.NewMessage(
                senderId = sender.userId,
                senderUsername = sender.username,
                recipientIds = chat.participants.map { it.userId }.toSet(),
                chatId = chatId,
                message = savedMessage.content,
            )
        )

        return savedMessage.toChatMessage()
    }

    @Transactional
    fun deleteMessage(
        messageId: ChatMessageId,
        requestUserId: UserId
    ) {
        val message = chatMessageRepository.findByIdOrNull(messageId)
            ?: throw MessageNotFoundException(messageId)

        if (message.sender.userId != requestUserId) {
            throw ForbiddenException()
        }

        chatMessageRepository.delete(message)

        applicationEventPublisher.publishEvent(
            MessageDeletedEvent(
                chatId = message.chatId,
                messageId = messageId
            )
        )

        evictMessagesCache(message.chatId)
    }

    @CacheEvict(
        value = [CHAT_MESSAGES_CACHE_NAME],
        key = "#chatId"
    )
    fun evictMessagesCache(chatId: ChatId) {
        // NO-OP: let Spring handle cache evict
    }
}