package com.example.intouchmobileapp.data.remote.dto

import java.time.LocalDateTime

data class Message(
    val author: User,
    val chatId: Int,
    val dateTime: LocalDateTime,
    val id: Int,
    val isAuxiliary: Boolean,
    val text: String
)