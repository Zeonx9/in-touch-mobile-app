package com.example.intouchmobileapp.presentation.chat_list

import androidx.navigation.NavController

sealed interface ChatListScreenEvent {
    data class ChatClicked(val navController: NavController, val chatId: Int) : ChatListScreenEvent
}