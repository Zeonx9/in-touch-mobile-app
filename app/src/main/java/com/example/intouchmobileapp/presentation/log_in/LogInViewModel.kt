package com.example.intouchmobileapp.presentation.log_in

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.intouchmobileapp.common.Resource
import com.example.intouchmobileapp.domain.use_case.login.LogInUseCase
import com.example.intouchmobileapp.domain.use_case.login.SaveCredentialsUseCase
import com.example.intouchmobileapp.domain.use_case.login.StartStompConnectionUseCase
import com.example.intouchmobileapp.presentation.Screen
import com.example.intouchmobileapp.presentation.common.navigateAndReplaceStartDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase,
    private val startStompConnectionUseCase: StartStompConnectionUseCase,
    private val saveCredentialsUseCase: SaveCredentialsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(LogInState())
    val state: State<LogInState> = _state

    fun onEvent(event: LogInScreenEvent) {
        when(event) {
            is LogInScreenEvent.LogInClickEvent -> logIn(event.navController)
            is LogInScreenEvent.LoginTextChangedEvent -> updateLogin(event.newLogin)
            is LogInScreenEvent.PasswordTextChanged -> updatePassword(event.newPassword)
        }
    }

    private fun logIn(navController: NavController) {
        logInUseCase(state.value.login, state.value.password).onEach { result ->
            when(result) {
                is Resource.Success -> onSuccessfulLogIn(navController)
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        error = "",
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun onSuccessfulLogIn(navController: NavController) {
        _state.value = _state.value.copy(
            error = "",
            isLoading = false
        )
        viewModelScope.launch {
            saveCredentialsUseCase(state.value.login, state.value.password)
        }
        startStompConnectionUseCase()
        navController.navigateAndReplaceStartDestination(Screen.ChatListScreen.route)
    }

    private fun updateLogin(newLogin: String) {
        _state.value = _state.value.copy(
            login = newLogin
        )
    }

    private fun updatePassword(newPassword: String) {
        _state.value = _state.value.copy(
            password = newPassword
        )
    }
}