package com.example.intouchmobileapp.domain.repository

import com.example.intouchmobileapp.data.remote.dto.ReadNotification
import com.example.intouchmobileapp.domain.model.Chat
import com.example.intouchmobileapp.domain.model.Message
import kotlinx.coroutines.flow.StateFlow

interface ChatRepository {

    val chats: StateFlow<List<Chat>>
    suspend fun fetchChats()
    suspend fun fetchPrivateChat(otherUserId: Int): Chat
    fun needToFetchChats(): Boolean
    fun onNewChatReceived(chat: Chat)
    fun onNewMessageReceived(message: Message)
    fun onReadNotificationReceived(notification: ReadNotification)
    fun clear()
}