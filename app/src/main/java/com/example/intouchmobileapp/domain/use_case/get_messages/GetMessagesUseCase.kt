package com.example.intouchmobileapp.domain.use_case.get_messages

import com.example.intouchmobileapp.common.Resource
import com.example.intouchmobileapp.domain.model.Message
import com.example.intouchmobileapp.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {
    operator fun invoke(chatId: Int): Flow<Resource<StateFlow<List<Message>>>> = flow {
        try {
            if (messageRepository.chatNeedToBeFetched(chatId)) {
                emit(Resource.Loading())
                messageRepository.fetchMessagesByChatId(chatId)
            }
            val messagesFlow = messageRepository.getChatMessagesById(chatId)
            emit(Resource.Success(messagesFlow))
        } catch (e: Exception) {
            emit(Resource.Error(e.message!!))
        }
    }
}