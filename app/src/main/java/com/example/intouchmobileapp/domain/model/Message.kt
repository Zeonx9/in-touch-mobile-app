package com.example.intouchmobileapp.domain.model

import java.time.LocalDateTime

data class Message(
    val author: User,
    val chatId: Int,
    val dateTime: LocalDateTime,
    val id: Int,
    val isAuxiliary: Boolean,
    val text: String
)