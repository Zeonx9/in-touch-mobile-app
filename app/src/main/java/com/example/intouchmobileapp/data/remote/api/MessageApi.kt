package com.example.intouchmobileapp.data.remote.api

import com.example.intouchmobileapp.data.remote.dto.ProtoMessage
import com.example.intouchmobileapp.domain.model.Message
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface MessageApi {
    @GET("chats/{id}/messages")
    suspend fun fetchChatMessages(
        @Path("id") chatId: Int,
        @Header("Authorization") authHeader: String
    ): List<Message>

    @POST("users/{userId}/chats/{chatId}/message")
    suspend fun sendMessage(
        @Body message: ProtoMessage,
        @Path("userId") userId: Int,
        @Path("chatId") chatId: Int,
        @Header("Authorization") authHeader: String
    )
}