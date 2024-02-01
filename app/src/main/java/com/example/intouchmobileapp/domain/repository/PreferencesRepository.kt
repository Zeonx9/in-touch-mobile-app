package com.example.intouchmobileapp.domain.repository

import com.example.intouchmobileapp.domain.model.AppPreferences
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    val preferences: Flow<AppPreferences>
    suspend fun updateCredentials(login: String, password: String)
    suspend fun updateLogged(isLogged: Boolean)
}