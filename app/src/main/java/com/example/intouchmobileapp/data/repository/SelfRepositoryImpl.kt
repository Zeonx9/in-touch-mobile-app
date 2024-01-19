package com.example.intouchmobileapp.data.repository

import com.example.intouchmobileapp.data.remote.dto.AuthResponse
import com.example.intouchmobileapp.data.remote.dto.Company
import com.example.intouchmobileapp.data.remote.dto.User
import com.example.intouchmobileapp.domain.repository.SelfRepository

class SelfRepositoryImpl : SelfRepository {
    private var self: User? = null
    private var company: Company? = null
    private var token: String? = null

    override fun setValues(data: AuthResponse) {
        self = data.user
        company = data.company
        token = data.token
    }

    override fun getSelf(): User {
        return self!!
    }

    override fun getSelfId(): Int {
        return self!!.id
    }

    override fun getCompanyId(): Int {
        return company!!.id
    }

    override fun getAuthHeader(): String {
        return "Bearer $token"
    }
}