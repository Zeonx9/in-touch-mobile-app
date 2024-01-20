package com.example.intouchmobileapp.data.repository

import android.util.Log
import com.example.intouchmobileapp.data.remote.api.ChatApi
import com.example.intouchmobileapp.data.remote.dto.Chat
import com.example.intouchmobileapp.data.remote.dto.UnreadCounter
import com.example.intouchmobileapp.domain.repository.ChatRepository
import com.example.intouchmobileapp.domain.repository.SelfRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatApi: ChatApi,
    private val selfRepository: SelfRepository
): ChatRepository {

    override suspend fun getChats(): List<Chat> {
        return chatApi.getChatsOfUser(selfRepository.selfId, selfRepository.authHeader)
    }

    override suspend fun getUnreadCounters(): List<UnreadCounter> {
        return chatApi.getUnreadCountersOfUser(selfRepository.selfId, selfRepository.authHeader)
    }

    override fun onNewChatReceived(chat: Chat) {
        Log.d(javaClass.name, "new chat with id=${chat.id} received")
    }
}