package com.example.intouchmobileapp.domain.repository

import com.example.intouchmobileapp.data.remote.dto.ConnectEvent
import com.example.intouchmobileapp.domain.model.User
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
    val users: StateFlow<List<User>>
    suspend fun fetchUsers()
    fun needToFetch(): Boolean
    fun onNewUserConnected(event: ConnectEvent)
    fun clear()
}