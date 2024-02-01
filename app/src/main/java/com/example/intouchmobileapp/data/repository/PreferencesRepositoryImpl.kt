package com.example.intouchmobileapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.intouchmobileapp.domain.model.AppPreferences
import com.example.intouchmobileapp.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    context: Context
) : PreferencesRepository {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val dataStore = context.dataStore

    companion object {
        private val loginKey = stringPreferencesKey("login")
        private val passwordKey = stringPreferencesKey("password")
        private val isLoggedKey = booleanPreferencesKey("is_logged")
    }

    override val preferences: Flow<AppPreferences> = combine(
        dataStore.data.map { it[loginKey] },
        dataStore.data.map { it[passwordKey] },
        dataStore.data.map { it[isLoggedKey] }
    )  { login, password, logged ->
        AppPreferences(
            login = login ?: "",
            password = password ?: "",
            isLogged = logged ?: false
        )
    }

    override suspend fun updateCredentials(login: String, password: String) {
        dataStore.edit {
            it[loginKey] = login
            it[passwordKey] = password
        }
    }

    override suspend fun updateLogged(isLogged: Boolean) {
        dataStore.edit {
            it[isLoggedKey] = isLogged
        }
    }
}