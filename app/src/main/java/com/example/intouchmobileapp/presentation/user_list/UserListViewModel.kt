package com.example.intouchmobileapp.presentation.user_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.intouchmobileapp.common.Resource
import com.example.intouchmobileapp.domain.use_case.get_chat.GetPrivateChatIdUseCase
import com.example.intouchmobileapp.domain.use_case.get_users.GetUsersUseCase
import com.example.intouchmobileapp.presentation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val getPrivateChatIdUseCase: GetPrivateChatIdUseCase
) : ViewModel() {

    private val _state = mutableStateOf(UserListState())
    val state: State<UserListState> = _state

    init {
        getUsers()
    }

    fun onEvent(event: UserListScreenEvent) {
        when(event) {
            is UserListScreenEvent.UserClickEvent -> {
                openPrivateChat(event.navController, userId = event.user.id)
            }
        }
    }

    private fun getUsers() {
        getUsersUseCase().onEach { result ->
            when(result) {
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message!!
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        users = result.data!!
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun openPrivateChat(navController: NavController, userId: Int) {
        getPrivateChatIdUseCase(userId).onEach { result ->
            when(result) {
                is Resource.Error -> _state.value = _state.value.copy(isLoading = false)
                is Resource.Loading -> _state.value = _state.value.copy(isLoading = true)
                is Resource.Success -> {
                    _state.value = _state.value.copy(isLoading = false)
                    navController.navigate(Screen.ChatScreen.withArgs(result.data!!))
                }
            }
        }.launchIn(viewModelScope)
    }
}