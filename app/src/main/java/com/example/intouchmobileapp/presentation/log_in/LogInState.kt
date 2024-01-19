package com.example.intouchmobileapp.presentation.log_in

data class LogInState (
    val login: String = "",
    val password: String = "",
    val error: String = "",
    val isLoading: Boolean = false
)