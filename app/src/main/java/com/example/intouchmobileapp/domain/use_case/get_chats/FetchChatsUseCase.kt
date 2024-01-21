package com.example.intouchmobileapp.domain.use_case.get_chats

import android.util.Log
import com.example.intouchmobileapp.common.Resource
import com.example.intouchmobileapp.domain.model.Chat
import com.example.intouchmobileapp.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchChatsUseCase @Inject constructor(
    private val chatRepository: ChatRepository,
) {
    operator fun invoke(): Flow<Resource<StateFlow<List<Chat>>>> = flow {
        try {
            emit(Resource.Loading())
            chatRepository.fetchChats()
            emit(Resource.Success(chatRepository.chats))
        } catch (e: Exception) {
            emit(Resource.Error(e.message!!))
            Log.e(javaClass.name, "exception caught!", e)
        }
    }
}