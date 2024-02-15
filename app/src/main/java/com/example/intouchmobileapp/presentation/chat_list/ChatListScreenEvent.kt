package com.example.intouchmobileapp.presentation.chat_list

import androidx.navigation.NavController
import com.example.intouchmobileapp.domain.model.Chat

sealed interface ChatListScreenEvent {
    data class ChatClicked(val navController: NavController, val chat: Chat) : ChatListScreenEvent
}