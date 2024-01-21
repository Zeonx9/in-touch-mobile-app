package com.example.intouchmobileapp.data.remote.api

import com.example.intouchmobileapp.domain.model.Chat
import com.example.intouchmobileapp.data.remote.dto.ConnectEvent
import com.example.intouchmobileapp.domain.model.Message
import com.example.intouchmobileapp.data.remote.dto.ReadNotification
import com.example.intouchmobileapp.domain.model.User


interface StompApi {
    fun connect()

    fun subscribeTopicConnection(companyId: Int, handler: (ConnectEvent) -> Unit)
    fun subscribeQueueMessages(selfId: Int, handler: (Message) -> Unit)
    fun subscribeQueueChats(selfId: Int, handler: (Chat) -> Unit)
    fun subscribeQueueReadNotifications(selfId: Int, handler: (ReadNotification) -> Unit)

    fun sendConnectSignal(user: User)
    fun sendDisconnectSignal(user: User)
    fun sendReadChatSignal(notification: ReadNotification)
}