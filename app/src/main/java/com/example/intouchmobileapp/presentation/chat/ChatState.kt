package com.example.intouchmobileapp.presentation.chat

import com.example.intouchmobileapp.domain.model.Chat
import com.example.intouchmobileapp.domain.model.Message

data class ChatState (
    val messages: List<Message> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false,
    val messageText: String = "",
    val chat: Chat? = null,
    val selfId: Int = -1
)