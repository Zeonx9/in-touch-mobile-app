package com.example.intouchmobileapp.presentation

sealed class Screen(val route: String) {
    data object LogInScreen : Screen("log_in_screen")
    data object ChatListScreen : Screen("chat_list_screen")
    data object ChatScreen : Screen("chat_screen")

    fun withArgs(vararg args: Any): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}