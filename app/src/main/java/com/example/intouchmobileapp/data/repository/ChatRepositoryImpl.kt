package com.example.intouchmobileapp.data.repository

import com.example.intouchmobileapp.data.remote.api.ChatApi
import com.example.intouchmobileapp.domain.model.Chat
import com.example.intouchmobileapp.domain.model.Message
import com.example.intouchmobileapp.domain.repository.ChatRepository
import com.example.intouchmobileapp.domain.repository.SelfRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatApi: ChatApi,
    private val selfRepository: SelfRepository
): ChatRepository {

    private val _chats: MutableStateFlow<List<Chat>> = MutableStateFlow(emptyList())
    override val chats: StateFlow<List<Chat>> = _chats

    override suspend fun fetchChats() {
        val chats = chatApi.getChatsOfUser(selfRepository.selfId, selfRepository.authHeader)
        val counters = chatApi.getUnreadCountersOfUser(selfRepository.selfId, selfRepository.authHeader)
        val counterById = counters.associateBy { it.chatId }

        _chats.update {
            chats.map {
                val count = counterById[it.id]?.count ?: 0
                it.copy(unreadCounter = count)
            }
        }
    }
    override fun onNewChatReceived(chat: Chat) {
        _chats.update { addNewChat(it, chat) }
    }

    private fun addNewChat(old: List<Chat>, chat: Chat): List<Chat> {
        val newList = ArrayList(old)
        newList.add(0, chat)
        return newList
    }

    override fun onNewMessageReceived(message: Message) {
        _chats.update { moveChatUpAndUpdateLastMessage(it, message) }
    }

    private fun moveChatUpAndUpdateLastMessage(old: List<Chat>, message: Message): List<Chat> {
        val chat = old.find { it.id == message.chatId }!!
        var newCounter = chat.unreadCounter
        if (message.author.id != selfRepository.selfId) {
            newCounter += 1
        }
        val newChat = chat.copy(
            lastMessage = message,
            unreadCounter = newCounter
        )
        val newList = ArrayList(old)
        newList.remove(chat)
        newList.add(0, newChat)
        return newList
    }


}