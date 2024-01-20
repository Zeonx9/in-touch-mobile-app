package com.example.intouchmobileapp.presentation.log_in

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intouchmobileapp.common.Resource
import com.example.intouchmobileapp.domain.use_case.login.LogInUseCase
import com.example.intouchmobileapp.domain.use_case.login.StartStompConnectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase,
    private val startStompConnectionUseCase: StartStompConnectionUseCase
) : ViewModel() {

    private val _state = mutableStateOf(LogInState())
    val state: State<LogInState> = _state

    fun logIn(successNavigation: () -> Unit) {
        logInUseCase(state.value.login, state.value.password).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        error = "",
                        isLoading = false
                    )
                    successNavigation()
                    startStompConnectionUseCase()
                }
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

    fun updateLogin(newLogin: String) {
        _state.value = _state.value.copy(
            login = newLogin
        )
    }

    fun updatePassword(newPassword: String) {
        _state.value = _state.value.copy(
            password = newPassword
        )
    }
}