package com.example.intouchmobileapp.data.remote.dto

data class User(
    val dateOfBirth: String,
    val id: Int,
    val isOnline: Boolean,
    val patronymic: String,
    val phoneNumber: String,
    val realName: String,
    val surname: String,
    val username: String
)