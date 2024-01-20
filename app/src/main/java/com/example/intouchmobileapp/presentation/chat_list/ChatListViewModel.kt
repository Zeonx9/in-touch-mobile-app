package com.example.intouchmobileapp.presentation.chat_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intouchmobileapp.common.Resource
import com.example.intouchmobileapp.domain.repository.SelfRepository
import com.example.intouchmobileapp.domain.use_case.get_chats.GetChatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val getChatsUseCase: GetChatsUseCase,
    selfRepository: SelfRepository
) : ViewModel() {

    private val _state = mutableStateOf(ChatListState())
    val state: State<ChatListState> = _state
    val selfId = selfRepository.selfId

    init {
        getChats()
    }

    private fun getChats() {
        getChatsUseCase().onEach { result ->
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
                        chats = result.data ?: emptyList()
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}