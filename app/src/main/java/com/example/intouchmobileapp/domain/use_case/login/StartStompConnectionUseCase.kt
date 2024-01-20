package com.example.intouchmobileapp.domain.use_case.login

import android.util.Log
import com.example.intouchmobileapp.data.remote.api.StompApi
import com.example.intouchmobileapp.domain.repository.ChatRepository
import com.example.intouchmobileapp.domain.repository.MessageRepository
import com.example.intouchmobileapp.domain.repository.SelfRepository
import com.example.intouchmobileapp.domain.repository.UserRepository
import javax.inject.Inject

class StartStompConnectionUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val chatRepository: ChatRepository,
    private val selfRepository: SelfRepository,
    private val messageRepository: MessageRepository,
    private val stompApi: StompApi
) {
    operator fun invoke() {
        stompApi.connect()
        stompApi.subscribeTopicConnection(selfRepository.companyId, userRepository::onNewUserConnected)
        stompApi.subscribeQueueChats(selfRepository.selfId, chatRepository::onNewChatReceived)
        stompApi.subscribeQueueMessages(selfRepository.selfId) {
            messageRepository.onNewMessageReceived(it)
            chatRepository.onNewMessageReceived(it)
        }
        stompApi.sendConnectSignal(selfRepository.user)
        Log.d(javaClass.name, "initiating stomp connection")
    }
}