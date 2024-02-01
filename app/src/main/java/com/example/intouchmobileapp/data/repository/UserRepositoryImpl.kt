package com.example.intouchmobileapp.data.repository

import com.example.intouchmobileapp.data.remote.api.UserApi
import com.example.intouchmobileapp.data.remote.dto.ConnectEvent
import com.example.intouchmobileapp.domain.model.User
import com.example.intouchmobileapp.domain.repository.SelfRepository
import com.example.intouchmobileapp.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val selfRepository: SelfRepository
) : UserRepository {

    override suspend fun getUsers(): List<User> {
        return userApi.getUsers(selfRepository.selfId, selfRepository.authHeader)
    }

    override fun onNewUserConnected(event: ConnectEvent) {

    }

    override fun clear() {

    }

}
