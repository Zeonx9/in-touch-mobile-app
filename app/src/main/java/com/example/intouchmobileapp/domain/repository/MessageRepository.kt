package com.example.intouchmobileapp.domain.repository

import com.example.intouchmobileapp.data.remote.dto.Message

interface MessageRepository {
    fun onNewMessageReceived(message: Message)

}