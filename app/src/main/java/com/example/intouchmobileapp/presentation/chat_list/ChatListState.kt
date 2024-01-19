package com.example.intouchmobileapp.presentation.chat_list

import com.example.intouchmobileapp.data.remote.dto.Chat

data class ChatListState (
    val chats: List<Chat> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)
