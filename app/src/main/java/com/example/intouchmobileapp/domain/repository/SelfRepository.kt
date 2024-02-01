package com.example.intouchmobileapp.domain.repository

import com.example.intouchmobileapp.data.remote.dto.AuthResponse
import com.example.intouchmobileapp.domain.model.User

interface SelfRepository {
    fun setValues(data: AuthResponse)

    fun clear()

    val user: User
    val selfId: Int
    val companyId: Int
    val authHeader: String

}