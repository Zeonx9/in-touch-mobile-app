package com.example.intouchmobileapp.domain.repository

import com.example.intouchmobileapp.data.remote.dto.Chat
import com.example.intouchmobileapp.data.remote.dto.Message
import kotlinx.coroutines.flow.StateFlow

interface ChatRepository {

    val chats: StateFlow<List<Chat>>
    suspend fun fetchChats()

    fun onNewChatReceived(chat: Chat)
    fun onNewMessageReceived(message: Message)
}