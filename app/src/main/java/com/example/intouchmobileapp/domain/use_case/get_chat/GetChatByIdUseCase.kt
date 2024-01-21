package com.example.intouchmobileapp.domain.use_case.get_chat

import com.example.intouchmobileapp.domain.model.Chat
import com.example.intouchmobileapp.domain.repository.ChatRepository
import javax.inject.Inject

class GetChatByIdUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(chatId: Int): Chat {
        return chatRepository.chats.value.find { it.id == chatId }!!
    }
}