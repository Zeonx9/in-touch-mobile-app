package com.example.intouchmobileapp.domain.use_case.get_chats

import android.util.Log
import com.example.intouchmobileapp.common.Resource
import com.example.intouchmobileapp.domain.model.Chat
import com.example.intouchmobileapp.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchChatsUseCase @Inject constructor(
    private val chatRepository: ChatRepository,
) {
    operator fun invoke(): Flow<Resource<List<Chat>>> = flow {
        try {
            if (chatRepository.needToFetchChats()) {
                emit(Resource.Loading())
                chatRepository.fetchChats()
            }
            emit(Resource.Success(chatRepository.chats.value))
        } catch (e: Exception) {
            emit(Resource.Error(e.message!!))
            Log.e(javaClass.name, "exception caught!", e)
        }
    }.combine(chatRepository.chats) { fetched, saved ->
        when(fetched) {
            is Resource.Success -> Resource.Success(saved)
            else -> fetched
        }
    }
}