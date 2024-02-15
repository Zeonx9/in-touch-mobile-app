package com.example.intouchmobileapp.presentation.chat

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intouchmobileapp.common.Constants
import com.example.intouchmobileapp.common.Resource
import com.example.intouchmobileapp.domain.model.Chat
import com.example.intouchmobileapp.domain.model.Message
import com.example.intouchmobileapp.domain.repository.SelfRepository
import com.example.intouchmobileapp.domain.use_case.get_chat.GetChatByIdUseCase
import com.example.intouchmobileapp.domain.use_case.get_messages.GetMessagesUseCase
import com.example.intouchmobileapp.domain.use_case.send_message.SendMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    chatByIdUseCase: GetChatByIdUseCase,
    selfRepository: SelfRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val chatId: Int = savedStateHandle[Constants.PARAM_CHAT_ID]!!
    private val _state: MutableState<ChatState> = mutableStateOf(ChatState())
    val state: State<ChatState> = _state
    val selfId = selfRepository.selfId
    val chat = chatByIdUseCase(chatId)

    init {
        fetchMessages(chatId)
    }

    private fun fetchMessages(chatId: Int) {
        getMessagesUseCase(chatId).onEach { result ->
            when(result) {
                is Resource.Error -> {
                    _state.value = ChatState(error = result.message!!)
                }
                is Resource.Loading -> {
                    _state.value = ChatState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = ChatState(messages = result.data!!)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateMessageText(text: String) {
        _state.value = _state.value.copy(
            messageText = text
        )
    }

    fun sendMessage() {
        if (state.value.messageText.isBlank()) {
            return
        }
        viewModelScope.launch {
            sendMessageUseCase(state.value.messageText, chatId)
        }
        updateMessageText("")
    }
}