package com.example.intouchmobileapp.presentation.chat

import androidx.navigation.NavController

sealed interface ChatScreenEvent {
    data class UpEvent(val navController: NavController) : ChatScreenEvent
    data class MessageTextChanged(val newMessageText: String) : ChatScreenEvent
    data object SendClicked : ChatScreenEvent
}