package com.example.intouchmobileapp.data.remote.api

import com.example.intouchmobileapp.data.remote.dto.AuthRequest
import com.example.intouchmobileapp.data.remote.dto.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    suspend fun logIn(@Body credentials: AuthRequest): AuthResponse


}