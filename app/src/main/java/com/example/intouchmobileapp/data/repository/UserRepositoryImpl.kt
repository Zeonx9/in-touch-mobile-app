package com.example.intouchmobileapp.data.repository

import com.example.intouchmobileapp.data.remote.api.UserApi
import com.example.intouchmobileapp.data.remote.dto.ConnectEvent
import com.example.intouchmobileapp.domain.model.User
import com.example.intouchmobileapp.domain.repository.SelfRepository
import com.example.intouchmobileapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val selfRepository: SelfRepository
) : UserRepository {

    private val _users: MutableStateFlow<List<User>> = MutableStateFlow(emptyList())
    override val users = _users.asStateFlow()

    override suspend fun fetchUsers() {
        val usersFromApi = userApi.getUsers(selfRepository.companyId, selfRepository.authHeader)
            .filter {
                it.id != selfRepository.selfId
            }
        _users.update { usersFromApi }
    }

    override fun needToFetch(): Boolean {
        return users.value.isEmpty()
    }

    override fun onNewUserConnected(event: ConnectEvent) {

    }

    override fun clear() {
        _users.update { emptyList() }
    }

}
