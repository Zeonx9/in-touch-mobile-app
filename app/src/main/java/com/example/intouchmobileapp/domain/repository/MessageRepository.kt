package com.example.intouchmobileapp.domain.repository

import com.example.intouchmobileapp.domain.model.Message
import kotlinx.coroutines.flow.StateFlow

interface MessageRepository {
    fun onNewMessageReceived(message: Message)

    fun getChatMessagesById(chatId: Int): StateFlow<List<Message>>

    fun chatNeedToBeFetched(chatId: Int): Boolean

    suspend fun fetchMessagesByChatId(chatId: Int)
    fun clear()
}