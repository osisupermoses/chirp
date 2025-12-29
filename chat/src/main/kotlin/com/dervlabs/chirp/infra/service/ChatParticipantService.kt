package com.dervlabs.chirp.infra.service

import com.dervlabs.chirp.domain.models.ChatParticipant
import com.dervlabs.chirp.domain.type.UserId
import com.dervlabs.chirp.infra.database.mappers.toChatParticipant
import com.dervlabs.chirp.infra.database.mappers.toChatParticipantEntity
import com.dervlabs.chirp.infra.database.repositories.ChatParticipantRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ChatParticipantService(
    private val chatParticipantRepository: ChatParticipantRepository,
) {

    fun createChatParticipant(
        chatParticipant: ChatParticipant,
    ) {
        chatParticipantRepository.save(
            chatParticipant.toChatParticipantEntity()
        )
    }

    fun findChatParticipantById(userId: UserId): ChatParticipant? {
        return chatParticipantRepository.findByIdOrNull(userId)?.toChatParticipant()
    }

    fun findChatParticipantByEmailOrUsername(
        query: String
    ): ChatParticipant? {
        val normalizedQuery = query.lowercase().trim()
        return chatParticipantRepository.findByEmailOrUsername(
            query = normalizedQuery
        )?.toChatParticipant()
    }
}