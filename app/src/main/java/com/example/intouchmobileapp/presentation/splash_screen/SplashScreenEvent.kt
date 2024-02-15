package com.example.intouchmobileapp.presentation.splash_screen

import androidx.navigation.NavController

sealed interface SplashScreenEvent {
    data class LogInEvent(val navController: NavController) : SplashScreenEvent
}