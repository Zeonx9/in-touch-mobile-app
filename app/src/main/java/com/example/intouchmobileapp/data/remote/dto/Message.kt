package com.example.intouchmobileapp.data.remote.dto

import com.google.gson.annotations.JsonAdapter
import java.time.LocalDateTime

data class Message(
    val author: User,
    val chatId: Int,
    val dateTime: String,
    val id: Int,
    val isAuxiliary: Boolean,
    val text: String
)