package com.example.intouchmobileapp.presentation.settings

import androidx.navigation.NavController

sealed interface SettingsScreenEvent {
    data class LogOutClickedEvent(val navController: NavController) : SettingsScreenEvent
}