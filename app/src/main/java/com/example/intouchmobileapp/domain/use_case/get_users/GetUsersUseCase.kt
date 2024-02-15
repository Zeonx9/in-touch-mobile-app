package com.example.intouchmobileapp.domain.use_case.get_users

import com.example.intouchmobileapp.common.Resource
import com.example.intouchmobileapp.domain.model.User
import com.example.intouchmobileapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(): Flow<Resource<List<User>>> = flow {
        try {
            if (userRepository.needToFetch()) {
                emit(Resource.Loading())
                userRepository.fetchUsers()
            }
            emit(Resource.Success(Unit))
        }
        catch (e: Exception) {
            emit(Resource.Error(e.message!!))
        }
    }.combine(userRepository.users) { fetch, saved ->
        when(fetch) {
            is Resource.Error -> Resource.Error(fetch.message!!)
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> Resource.Success(saved)
        }
    }
}