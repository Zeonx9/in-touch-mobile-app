package com.example.intouchmobileapp.presentation.log_in

import androidx.navigation.NavController

sealed interface LogInScreenEvent {
    data class LoginTextChangedEvent(val newLogin: String) : LogInScreenEvent
    data class PasswordTextChanged(val newPassword: String) : LogInScreenEvent
    data class LogInClickEvent(val navController: NavController) : LogInScreenEvent
}