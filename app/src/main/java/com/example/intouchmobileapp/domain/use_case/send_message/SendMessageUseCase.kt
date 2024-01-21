package com.example.intouchmobileapp.domain.use_case.send_message

import com.example.intouchmobileapp.data.remote.api.MessageApi
import com.example.intouchmobileapp.data.remote.dto.ProtoMessage
import com.example.intouchmobileapp.domain.repository.SelfRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val messageApi: MessageApi,
    private val selfRepository: SelfRepository
) {
    suspend operator fun invoke(text: String, chatId: Int) {
        val message = ProtoMessage(text = text)
        messageApi.sendMessage(
            message,
            selfRepository.selfId,
            chatId,
            selfRepository.authHeader
        )
    }
}
