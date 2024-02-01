package com.example.intouchmobileapp.data.repository

import com.example.intouchmobileapp.data.remote.api.MessageApi
import com.example.intouchmobileapp.domain.model.Message
import com.example.intouchmobileapp.domain.repository.MessageRepository
import com.example.intouchmobileapp.domain.repository.SelfRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val messageApi: MessageApi,
    private val selfRepository: SelfRepository
) : MessageRepository {
    private val messagesByChatId: MutableMap<Int, MutableStateFlow<List<Message>>> = HashMap()
    override fun getChatMessagesById(chatId: Int): StateFlow<List<Message>> {
        return messagesByChatId[chatId]!!
    }

    override fun chatNeedToBeFetched(chatId: Int): Boolean {
        return !messagesByChatId.containsKey(chatId)
    }

    override suspend fun fetchMessagesByChatId(chatId: Int) {
        val messages = messageApi.fetchChatMessages(chatId, selfRepository.authHeader)
        messagesByChatId[chatId] = MutableStateFlow(messages)
    }

    override fun onNewMessageReceived(message: Message) {
        if (!messagesByChatId.containsKey(message.chatId)) {
            return
        }
        messagesByChatId[message.chatId]?.update { addNewMessage(it, message) }
    }

    private fun addNewMessage(oldList: List<Message>, message: Message): List<Message> {
        val newList = ArrayList(oldList)
        newList.add(message)
        return newList
    }



}