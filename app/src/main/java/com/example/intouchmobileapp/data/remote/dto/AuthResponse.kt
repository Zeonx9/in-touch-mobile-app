package com.example.intouchmobileapp.data.remote.dto

import com.example.intouchmobileapp.domain.model.Company
import com.example.intouchmobileapp.domain.model.User
import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("admin")
    val isAdmin: Boolean,
    val company: Company,
    val token: String,
    val user: User
)