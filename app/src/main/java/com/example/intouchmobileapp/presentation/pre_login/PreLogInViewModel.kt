package com.example.intouchmobileapp.presentation.pre_login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intouchmobileapp.common.Resource
import com.example.intouchmobileapp.domain.repository.PreferencesRepository
import com.example.intouchmobileapp.domain.use_case.login.LogInUseCase
import com.example.intouchmobileapp.domain.use_case.login.StartStompConnectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class PreLogInViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase,
    private val startStompConnectionUseCase: StartStompConnectionUseCase,
    preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val preferences = runBlocking(Dispatchers.IO) {
        preferencesRepository.preferences.first()
    }

    fun tryLogin(successNavigation: () -> Unit, errorNavigation: () -> Unit) {
        Log.i(javaClass.name, "logged = ${preferences.isLogged}, login = ${preferences.login}, password = ${preferences.password}")

        if (!preferences.isLogged) {
            viewModelScope.launch {
                delay(300)
                errorNavigation()
            }
            return
        }

        logInUseCase(preferences.login, preferences.password).onEach {
            when(it) {
                is Resource.Error ->  {
                    errorNavigation()
                }
                is Resource.Success -> {
                    successNavigation()
                    startStompConnectionUseCase()
                }
                else -> Unit
            }
        }.launchIn(viewModelScope)
    }
}