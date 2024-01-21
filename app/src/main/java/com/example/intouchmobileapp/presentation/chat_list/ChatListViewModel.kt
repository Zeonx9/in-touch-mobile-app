package com.example.intouchmobileapp.presentation.chat_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intouchmobileapp.common.Resource
import com.example.intouchmobileapp.domain.model.Chat
import com.example.intouchmobileapp.domain.repository.SelfRepository
import com.example.intouchmobileapp.domain.use_case.get_chats.FetchChatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val fetchChatsUseCase: FetchChatsUseCase,
    selfRepository: SelfRepository
) : ViewModel() {

    private val _state = mutableStateOf(ChatListState())
    val state: State<ChatListState> = _state
    val selfId = selfRepository.selfId
    private var chatObservingJob: Job? = null

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
                        isLoading = false
                    )
                    observeChats(result.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun observeChats(flow: StateFlow<List<Chat>>?) {
        chatObservingJob?.let {
            it.cancel()
            Log.i(javaClass.name, "job watching chats stateflow was cancelled")
        }
        chatObservingJob = flow?.onEach {
            _state.value = _state.value.copy(
                chats = it
            )
        }?.launchIn(viewModelScope)
    }
}