package com.example.intouchmobileapp.presentation.chat

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intouchmobileapp.common.Constants
import com.example.intouchmobileapp.common.Resource
import com.example.intouchmobileapp.domain.repository.SelfRepository
import com.example.intouchmobileapp.domain.use_case.get_chat.GetChatByIdUseCase
import com.example.intouchmobileapp.domain.use_case.get_messages.GetMessagesUseCase
import com.example.intouchmobileapp.domain.use_case.send_message.SendMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val getChatByIdUseCase: GetChatByIdUseCase,
    selfRepository: SelfRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val chatId: Int = savedStateHandle[Constants.PARAM_CHAT_ID]!!
    private val _state: MutableState<ChatState> = mutableStateOf(
        ChatState(selfId = selfRepository.selfId)
    )
    val state: State<ChatState> = _state

    init {
        getChat()
        getMessages()
    }

    fun onEvent(event: ChatScreenEvent) {
        when(event) {
            is ChatScreenEvent.MessageTextChanged -> updateMessageText(event.newMessageText)
            ChatScreenEvent.SendClicked -> sendMessage()
            is ChatScreenEvent.UpEvent -> event.navController.navigateUp()
        }
    }

    private fun getMessages() {
        getMessagesUseCase(chatId).onEach { result ->
            when(result) {
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = result.message!!)
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(isLoading = false, messages = result.data!!)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getChat() {
        getChatByIdUseCase(chatId).onEach {
            _state.value = _state.value.copy(chat = it)
        }.launchIn(viewModelScope)
    }

    private fun updateMessageText(text: String) {
        _state.value = _state.value.copy(messageText = text)
    }

    private fun sendMessage() {
        if (state.value.messageText.isBlank()) {
            return
        }
        viewModelScope.launch {
            sendMessageUseCase(state.value.messageText, chatId)
        }
        updateMessageText("")
    }
}