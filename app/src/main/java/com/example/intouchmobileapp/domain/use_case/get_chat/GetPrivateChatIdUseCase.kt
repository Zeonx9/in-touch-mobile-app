package com.example.intouchmobileapp.domain.use_case.get_chat

import android.util.Log
import com.example.intouchmobileapp.common.Resource
import com.example.intouchmobileapp.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPrivateChatIdUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {

    operator fun invoke(otherUserId: Int): Flow<Resource<Int>> = flow {
        try {
            emit(Resource.Loading())
            var chat = chatRepository.chats.value.find {
                it.isPrivate && it.members.find { member -> member.id == otherUserId } != null
            }
            if (chat != null) {
                emit(Resource.Success(chat.id))
            }
            else {
                chat = chatRepository.fetchPrivateChat(otherUserId)
                emit(Resource.Success(chat.id))
            }
        }
        catch (e: Exception) {
            emit(Resource.Error(e.message!!))
            Log.e(javaClass.name, "exception caught!", e)
        }
    }
}