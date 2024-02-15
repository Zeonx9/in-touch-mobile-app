package com.example.intouchmobileapp.presentation.chat_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.intouchmobileapp.common.Resource
import com.example.intouchmobileapp.domain.repository.SelfRepository
import com.example.intouchmobileapp.domain.use_case.get_chats.FetchChatsUseCase
import com.example.intouchmobileapp.domain.use_case.send_read_signal.SendReadSignalUseCase
import com.example.intouchmobileapp.presentation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val fetchChatsUseCase: FetchChatsUseCase,
    private val sendReadSignalUseCase: SendReadSignalUseCase,
    selfRepository: SelfRepository
) : ViewModel() {

    private val _state = mutableStateOf(ChatListState())
    val state: State<ChatListState> = _state
    val selfId = selfRepository.selfId

    init {
        fetchChats()
    }

    private fun fetchChats() {
        fetchChatsUseCase().onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message ?: ""
                    )
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        chats = result.data!!
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun openChat(navController: NavController, chatId: Int) {
        navController.navigate(route = Screen.ChatScreen.withArgs(chatId))
        sendReadSignalUseCase(chatId, selfId)
    }
}