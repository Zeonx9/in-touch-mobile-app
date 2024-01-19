package com.example.intouchmobileapp.domain.use_case.login

import android.util.Log
import com.example.intouchmobileapp.common.Resource
import com.example.intouchmobileapp.data.remote.api.AuthApi
import com.example.intouchmobileapp.data.remote.dto.AuthRequest
import com.example.intouchmobileapp.domain.repository.SelfRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogInUseCase @Inject constructor(
    private val authApi: AuthApi,
    private val selfRepository: SelfRepository
) {
    operator fun invoke(login: String, password: String): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            val response = authApi.logIn(AuthRequest(login, password))
            selfRepository.setValues(response)
            emit(Resource.Success(true))
        } catch (e: Exception) {
            Log.e("Log In Use Case", "exception caught", e)
            emit(Resource.Error("error occurred: ${e.message}"))
        }
    }
}