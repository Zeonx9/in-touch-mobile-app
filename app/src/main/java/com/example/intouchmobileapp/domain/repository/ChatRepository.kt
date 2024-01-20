package com.example.intouchmobileapp.domain.repository

import com.example.intouchmobileapp.data.remote.dto.Chat
import com.example.intouchmobileapp.data.remote.dto.UnreadCounter

interface ChatRepository {
    suspend fun getChats(): List<Chat>

    suspend fun getUnreadCounters(): List<UnreadCounter>

    fun onNewChatReceived(chat: Chat)
}