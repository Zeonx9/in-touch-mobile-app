package com.example.intouchmobileapp.data.remote.dto

import java.time.LocalDate

data class User(
    val dateOfBirth: LocalDate,
    val id: Int,
    val isOnline: Boolean,
    val patronymic: String,
    val phoneNumber: String,
    val realName: String,
    val surname: String,
    val username: String
)