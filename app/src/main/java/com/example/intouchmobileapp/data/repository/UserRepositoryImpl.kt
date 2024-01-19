package com.example.intouchmobileapp.data.repository

import com.example.intouchmobileapp.data.remote.api.AuthApi
import com.example.intouchmobileapp.data.remote.api.UserApi
import com.example.intouchmobileapp.data.remote.dto.AuthRequest
import com.example.intouchmobileapp.data.remote.dto.AuthResponse
import com.example.intouchmobileapp.data.remote.dto.User
import com.example.intouchmobileapp.domain.repository.SelfRepository
import com.example.intouchmobileapp.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val selfRepository: SelfRepository
) : UserRepository {

    override suspend fun getUsers(): List<User> {
        return userApi.getUsers(selfRepository.getCompanyId(), selfRepository.getAuthHeader())
    }
}
