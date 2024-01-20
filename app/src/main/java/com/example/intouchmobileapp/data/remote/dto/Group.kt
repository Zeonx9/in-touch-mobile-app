package com.example.intouchmobileapp.data.remote.dto

import java.time.LocalDate

data class Group(
    val creationDate: LocalDate,
    val creator: User,
    val name: String
)