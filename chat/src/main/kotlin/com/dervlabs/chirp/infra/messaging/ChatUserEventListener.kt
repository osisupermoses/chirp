package com.dervlabs.chirp.infra.messaging

import com.dervlabs.chirp.domain.events.user.UserEvent
import com.dervlabs.chirp.domain.models.ChatParticipant
import com.dervlabs.chirp.infra.message_queue.MessageQueues
import com.dervlabs.chirp.infra.service.ChatParticipantService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ChatUserEventListener(
    private val chatParticipantService: ChatParticipantService
) {

    @RabbitListener(queues = [MessageQueues.CHAT_USER_EVENTS])
    @Transactional
    fun handleUserEvent(event: UserEvent) {
        when(event) {
            is UserEvent.Verified -> {
                chatParticipantService.createChatParticipant(
                    chatParticipant = ChatParticipant(
                        userId = event.userId,
                        username = event.username,
                        email = event.email,
                        profilePictureUrl = null
                    )
                )
            }
            else -> Unit
        }
    }
}