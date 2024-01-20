package com.example.intouchmobileapp.data.repository

import com.example.intouchmobileapp.data.remote.dto.Message
import com.example.intouchmobileapp.domain.repository.MessageRepository
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(

) : MessageRepository {
    override fun onNewMessageReceived(message: Message) {

    }

}