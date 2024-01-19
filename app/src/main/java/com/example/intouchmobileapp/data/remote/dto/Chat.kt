package com.example.intouchmobileapp.data.remote.dto

data class Chat(
    val id: Int,
    val group: Group?,
    val isPrivate: Boolean,
    val lastMessage: Message,
    val members: List<User>,
    val unreadCounter: Int = 0
)