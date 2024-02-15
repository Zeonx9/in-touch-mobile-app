package com.example.intouchmobileapp.data.remote.api

import com.example.intouchmobileapp.domain.model.User
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface UserApi {

    @GET("company/{id}/users")
    suspend fun getUsers(@Path("id") companyId: Int, @Header("Authorization") authHeader: String): List<User>
}