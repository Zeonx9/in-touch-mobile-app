package com.example.intouchmobileapp.domain.repository

import com.example.intouchmobileapp.data.remote.dto.AuthResponse
import com.example.intouchmobileapp.data.remote.dto.User

interface SelfRepository {
    fun setValues(data: AuthResponse)

    fun getSelf(): User

    fun getSelfId(): Int

    fun getCompanyId(): Int

    fun getAuthHeader(): String
}