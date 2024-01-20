package com.example.intouchmobileapp.domain.repository

import com.example.intouchmobileapp.data.remote.dto.ConnectEvent
import com.example.intouchmobileapp.data.remote.dto.User

interface UserRepository {
    suspend fun getUsers(): List<User>

    fun onNewUserConnected(event: ConnectEvent)
}