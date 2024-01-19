package com.example.intouchmobileapp.domain.use_case.get_chats

import android.util.Log
import com.example.intouchmobileapp.common.Resource
import com.example.intouchmobileapp.data.remote.dto.Chat
import com.example.intouchmobileapp.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetChatsUseCase @Inject constructor(
    private val chatRepository: ChatRepository,
) {

    operator fun invoke(): Flow<Resource<List<Chat>>> = flow {
        try {
            emit(Resource.Loading())
            val chats = chatRepository.getChats()
            val counters = chatRepository.getUnreadCounters()

            val counterById = counters.associateBy { it.chatId }
            val chatsWithCounters = chats.map {
                val count = counterById[it.id]?.count ?: 0
                it.copy(
                    unreadCounter = count
                )
            }
            emit(Resource.Success(chatsWithCounters))
        } catch (e: Exception) {
            Log.e(javaClass.name, "error occurred", e)
            emit(Resource.Error("error occurred: ${e.message}"))
        }
    }
}