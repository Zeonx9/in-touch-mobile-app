package com.example.intouchmobileapp.presentation

sealed class Screen(val route: String) {
    data object LogInScreen : Screen("log_in_screen")
    data object ChatListScreen : Screen("chat_list_screen")
}