package com.dervlabs.chirp.infra.service

import com.dervlabs.chirp.api.dto.ChatMessageDto
import com.dervlabs.chirp.api.mappers.toChatMessageDto
import com.dervlabs.chirp.domain.events.ChatParticipantJoinedEvent
import com.dervlabs.chirp.domain.events.ChatParticipantLeftEvent
import com.dervlabs.chirp.domain.exceptions.ChatNotFoundException
import com.dervlabs.chirp.domain.exceptions.ChatParticipantNotFound
import com.dervlabs.chirp.domain.exceptions.ForbiddenException
import com.dervlabs.chirp.domain.exceptions.InvalidChatSizeException
import com.dervlabs.chirp.domain.models.Chat
import com.dervlabs.chirp.domain.models.ChatMessage
import com.dervlabs.chirp.domain.type.ChatId
import com.dervlabs.chirp.domain.type.UserId
import com.dervlabs.chirp.infra.database.entities.ChatEntity
import com.dervlabs.chirp.infra.database.mappers.toChat
import com.dervlabs.chirp.infra.database.mappers.toChatMessage
import com.dervlabs.chirp.infra.database.repositories.ChatMessageRepository
import com.dervlabs.chirp.infra.database.repositories.ChatParticipantRepository
import com.dervlabs.chirp.infra.database.repositories.ChatRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import kotlin.collections.get

const val CHAT_MESSAGES_CACHE_NAME = "chat_messages"

@Service
class ChatService(
    private val chatRepository: ChatRepository,
    private val chatParticipantRepository: ChatParticipantRepository,
    private val chatMessageRepository: ChatMessageRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {


    @Cacheable(
        value = [CHAT_MESSAGES_CACHE_NAME],
        key = "#chatId", // '#chatId' represents parameter passed to getChatMessages(..) function
        condition = "#before == null && #pageSize <= 50", // only when this condition is true will redis responds with the cache messages else the call goes to our postgres db
        sync = true // if two simultaneous requests come in at the same time, it'll wait for the first query to finish, then respond with cache gotten from the first call
    )
    fun getChatMessages(
        chatId: ChatId,
        before: Instant? = null,
        pageSize: Int
    ): List<ChatMessageDto> {
        return chatMessageRepository
            .findByChatIdBefore(
                chatId = chatId,
                before = before ?: Instant.now(),
                pageable = PageRequest.of(0, pageSize)
            )
            .content
            .asReversed()
            .map { it.toChatMessage().toChatMessageDto() }
    }

    fun getChatById(
        chatId: ChatId,
        requestUserId: UserId,
    ): Chat? {
        return chatRepository
            .findChatById(chatId, requestUserId)
            ?.toChat(lastMessageForChat(chatId))
    }

    fun findChatsByUser(userId: UserId): List<Chat> {
        val chatEntities = chatRepository.findAllByUserId(userId)
        val chatIds = chatEntities.mapNotNull { it.id }
        val latestMessages = chatMessageRepository
            .findLatestMessagesByChatIds(chatIds.toSet())
            .associateBy { it.chatId }

        return chatEntities
            .map { it.toChat(lastMessage = latestMessages[it.id]?.toChatMessage()) }
            .sortedByDescending { it.lastActivityAt }
    }

    @Transactional
    fun createChat(
        creatorId: UserId,
        otherUserIds: Set<UserId>
    ): Chat {
        val otherParticipants = chatParticipantRepository.findByUserIdIn(
            userIds = otherUserIds
        )

        val allParticipants = (otherParticipants + creatorId)
        if (allParticipants.size < 2) {
            throw InvalidChatSizeException()
        }

        val creator = chatParticipantRepository.findByIdOrNull(creatorId)
            ?: throw ChatParticipantNotFound(creatorId)

        return chatRepository.save(
            ChatEntity(
                creator = creator,
                participants = setOf(creator) + otherParticipants
            )
        ).toChat(lastMessage = null)
    }

    @Transactional
    fun addParticipantsToChat(
        requestUserId: UserId,
        chatId: ChatId,
        userIds: Set<UserId>
    ): Chat {
        val chat = chatRepository.findByIdOrNull(chatId)
            ?: throw ChatNotFoundException()

        val isRequestingUserInChat = chat.participants.any {
            it.userId == requestUserId
        }
        if (!isRequestingUserInChat) {
            throw ForbiddenException()
        }

        val users = userIds.map { userId ->
            chatParticipantRepository.findByIdOrNull(userId)
                ?: throw ChatParticipantNotFound(userId)
        }

        val lastMessage = lastMessageForChat(chatId)
        val updatedChat = chatRepository.save(
            chat.apply {
                participants = chat.participants + users
            }
        ).toChat(lastMessage)

        applicationEventPublisher.publishEvent(
            ChatParticipantJoinedEvent(
                chatId = chatId,
                userIds = userIds
            )
        )

        return updatedChat
    }

    @Transactional
    fun removeParticipantFromChat(
        chatId: ChatId,
        userId: UserId
    ) {
        val chat = chatRepository.findByIdOrNull(chatId)
            ?: throw ChatNotFoundException()
        val participant = chat.participants.find { it.userId == userId }
            ?: throw ChatParticipantNotFound(userId)

        val newParticipantsSize = chat.participants.size - 1
        if (newParticipantsSize == 0) {
            chatRepository.deleteById(chatId)
            return
        }

        chatRepository.save(
            chat.apply {
                participants = chat.participants - participant
            }
        )

        applicationEventPublisher.publishEvent(
            ChatParticipantLeftEvent(
                chatId = chatId,
                userId = userId
            )
        )
    }

    private fun lastMessageForChat(chatId: ChatId): ChatMessage? {
        return chatMessageRepository
            .findLatestMessagesByChatIds(setOf(chatId))
            .firstOrNull()
            ?.toChatMessage()
    }
}