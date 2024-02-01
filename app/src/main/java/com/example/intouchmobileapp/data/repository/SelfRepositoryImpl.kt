package com.example.intouchmobileapp.data.repository

import com.example.intouchmobileapp.data.remote.dto.AuthResponse
import com.example.intouchmobileapp.domain.model.Company
import com.example.intouchmobileapp.domain.model.User
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

    override fun clear() {
        self = null
        company = null
        token = null
    }

    override val user: User
        get() = self!!

    override val selfId: Int
        get() = user.id

    override val authHeader: String
        get() = "Bearer $token"

    override val companyId: Int
        get() = company!!.id

}