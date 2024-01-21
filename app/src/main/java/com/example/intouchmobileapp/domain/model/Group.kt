package com.example.intouchmobileapp.domain.model

import java.time.LocalDate

data class Group(
    val creationDate: LocalDate,
    val creator: User,
    val name: String
)