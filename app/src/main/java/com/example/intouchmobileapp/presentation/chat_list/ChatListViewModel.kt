package com.example.intouchmobileapp.presentation.chat_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.intouchmobileapp.common.Resource.Error
import com.example.intouchmobileapp.common.Resource.Loading
import com.example.intouchmobileapp.common.Resource.Success
import com.example.intouchmobileapp.domain.repository.SelfRepository
import com.example.intouchmobileapp.domain.use_case.get_chats.GetChatsUseCase
import com.example.intouchmobileapp.domain.use_case.send_read_signal.SendReadSignalUseCase
import com.example.intouchmobileapp.presentation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val getChatsUseCase: GetChatsUseCase,
    private val sendReadSignalUseCase: SendReadSignalUseCase,
    selfRepository: SelfRepository
) : ViewModel() {

    private val _state = mutableStateOf(ChatListState(selfId = selfRepository.selfId))
    val state: State<ChatListState> = _state

    init {
        getChats()
    }

    fun onEvent(event: ChatListScreenEvent) {
        when(event) {
            is ChatListScreenEvent.ChatClicked -> openChat(event.navController, event.chatId)
        }
    }

    private fun getChats() {
        getChatsUseCase().onEach { result ->
            when(result) {
                is Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message ?: ""
                    )
                }
                is Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        chats = result.data!!
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun openChat(navController: NavController, chatId: Int) {
        sendReadSignalUseCase(chatId, state.value.selfId)
        navController.navigate(route = Screen.ChatScreen.withArgs(chatId))
    }
}