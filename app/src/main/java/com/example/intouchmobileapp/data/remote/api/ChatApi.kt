package com.example.intouchmobileapp.data.remote.api

import com.example.intouchmobileapp.domain.model.Chat
import com.example.intouchmobileapp.data.remote.dto.UnreadCounter
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ChatApi {
    @GET("users/{id}/chats")
    suspend fun getChatsOfUser(
        @Path("id") selfId: Int,
        @Header("Authorization") authHeader: String
    ): List<Chat>

    @GET("users/{id}/chats/unread")
    suspend fun getUnreadCountersOfUser(
        @Path("id") selfId: Int,
        @Header("Authorization") authHeader: String
    ): List<UnreadCounter>
}