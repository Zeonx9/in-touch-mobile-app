package com.example.intouchmobileapp.domain.use_case.get_chat

import com.example.intouchmobileapp.domain.model.Chat
import com.example.intouchmobileapp.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetChatByIdUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(chatId: Int): Flow<Chat> {
        return chatRepository.chats.map { list ->
            list.find { it.id == chatId }!!
        }
    }
}