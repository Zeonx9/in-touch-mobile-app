package com.example.intouchmobileapp.presentation.splash_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.intouchmobileapp.common.Resource
import com.example.intouchmobileapp.domain.repository.PreferencesRepository
import com.example.intouchmobileapp.domain.use_case.login.LogInUseCase
import com.example.intouchmobileapp.domain.use_case.login.StartStompConnectionUseCase
import com.example.intouchmobileapp.presentation.Screen.ChatListScreen
import com.example.intouchmobileapp.presentation.Screen.LogInScreen
import com.example.intouchmobileapp.presentation.common.navigateAndReplaceStartDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase,
    private val startStompConnectionUseCase: StartStompConnectionUseCase,
    preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val preferences = runBlocking(Dispatchers.IO) {
        preferencesRepository.preferences.first()
    }

    fun onEvent(event: SplashScreenEvent) {
        when(event) {
            is SplashScreenEvent.LogInEvent -> tryLogin(event.navController)
        }
    }

    private fun tryLogin(navController: NavController) {
        if (!preferences.isLogged) {
            navController.navigateAndReplaceStartDestination(LogInScreen.route)
        }
        else {
            logInUseCase(preferences.login, preferences.password).onEach {
                when(it) {
                    is Resource.Error ->  {
                        navController.navigateAndReplaceStartDestination(LogInScreen.route)
                    }
                    is Resource.Success -> {
                        startStompConnectionUseCase()
                        navController.navigateAndReplaceStartDestination(ChatListScreen.route)
                    }
                    else -> Unit
                }
            }.launchIn(viewModelScope)
        }
    }
}