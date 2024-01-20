package com.example.intouchmobileapp.data.remote.stomp

import android.util.Log
import com.example.intouchmobileapp.data.remote.api.StompApi
import com.example.intouchmobileapp.data.remote.dto.Chat
import com.example.intouchmobileapp.data.remote.dto.ConnectEvent
import com.example.intouchmobileapp.data.remote.dto.Message
import com.example.intouchmobileapp.data.remote.dto.ReadNotification
import com.example.intouchmobileapp.data.remote.dto.User
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompMessage
import javax.inject.Inject
import kotlin.reflect.KClass

class StompApiImpl @Inject constructor(
    private val stompClient: StompClient,
    private val gson: Gson
): StompApi {

    private val compositeDisposable = CompositeDisposable()
    private val subQueue: MutableList<() -> Unit> = ArrayList()
    private val sendQueue: MutableList<() -> Unit> = ArrayList()

    init {
        val lifecycleSubscription = stompClient.lifecycle().subscribe(
            {
                when(it.type) {
                    LifecycleEvent.Type.OPENED -> {
                        subQueue.forEach { it() }
                        subQueue.clear()

                        sendQueue.forEach{ it() }
                        sendQueue.clear()
                    }
                    LifecycleEvent.Type.ERROR -> {
                        Log.e(javaClass.name, "exception caught!", it.exception)
                    }
                    else -> {}
                }
            },
            {
                Log.e(javaClass.name, "exception caught!", it)
            }
        )
        compositeDisposable.add(lifecycleSubscription)
    }

    private fun <T : Any> typedHandler (
        klass: KClass<T>,
        handler: (T) -> Unit,
        logger: (T) -> Unit = {}
    ): (StompMessage) -> Unit {
        return {
            val payload = gson.fromJson(it.payload, klass.java)
            logger(payload)
            handler(payload)
        }
    }

    private fun <T: Any> subscribe(
        destination: String,
        kClass: KClass<T>,
        handler: (T) -> Unit,
        logger: (T) -> Unit = {},
        errorHandler: (Throwable) -> Unit = {}
    ) {
        val subscriptionCode: () -> Unit = {
            val subscription = stompClient.topic(destination)
                .subscribe(typedHandler(kClass, handler, logger), errorHandler)
            compositeDisposable.add(subscription)
        }
        if (stompClient.isConnected) {
            subscriptionCode()
        } else {
            subQueue.add(subscriptionCode)
        }
    }

    private fun <T: Any> send(
        destination: String,
        payload: T
    ) {
        val jsonString = gson.toJson(payload)
        val sendCode: () -> Unit = {
            val completable = stompClient.send(destination, jsonString)
            val disposable = completable.subscribe {
                Log.i(javaClass.name, "send completed")
            }
            compositeDisposable.add(disposable)
        }
        if (stompClient.isConnected) {
            sendCode()
        } else {
            sendQueue.add(sendCode)
        }
    }
    override fun connect() {
        stompClient.connect()
    }
    override fun subscribeTopicConnection(companyId: Int, handler: (ConnectEvent) -> Unit) {
        subscribe("/topic/connection", ConnectEvent::class, handler, {
            Log.i(javaClass.name, "received new connection id=${it.userId} connect=${it.connect}")
        })
    }

    override fun subscribeQueueMessages(selfId: Int, handler: (Message) -> Unit) {
        subscribe("/user/${selfId}/queue/messages", Message::class, handler)
    }

    override fun subscribeQueueChats(selfId: Int, handler: (Chat) -> Unit) {
        subscribe("/user/${selfId}/queue/chats", Chat::class, handler)
    }

    override fun subscribeQueueReadNotifications(selfId: Int, handler: (ReadNotification) -> Unit) {
        subscribe("/user/%d/queue/read_notifications", ReadNotification::class, handler)
    }

    override fun sendConnectSignal(user: User) {
        send("/app/connect", user)
    }

    override fun sendDisconnectSignal(user: User) {
        send("/app/disconnect", user)
    }

    override fun sendReadChatSignal(notification: ReadNotification) {
        send("/app/read_chat", notification)
    }
}