package com.example.intouchmobileapp.domain.use_case.send_read_signal

import com.example.intouchmobileapp.data.remote.api.StompApi
import com.example.intouchmobileapp.data.remote.dto.ReadNotification
import javax.inject.Inject

class SendReadSignalUseCase @Inject constructor(
    private val stompApi: StompApi
) {
    operator fun invoke(chatId: Int, userId: Int) {
        stompApi.sendReadChatSignal(
            ReadNotification(userId, chatId)
        )
    }
}