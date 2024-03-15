package com.example.intouchmobileapp.data.remote.stomp

import com.example.intouchmobileapp.data.remote.api.StompApi
import com.example.intouchmobileapp.data.remote.dto.ConnectEvent
import com.example.intouchmobileapp.data.remote.dto.ReadNotification
import com.example.intouchmobileapp.domain.model.Chat
import com.example.intouchmobileapp.domain.model.Message
import com.example.intouchmobileapp.domain.model.User
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompMessage
import javax.inject.Inject
import kotlin.reflect.KClass

class StompApiImpl @Inject constructor(
    private val stompClient: StompClient,
    private val gson: Gson
): StompApi {

    private lateinit var compositeDisposable: CompositeDisposable
    private val subQueue: MutableList<() -> Unit> = ArrayList()
    private val sendQueue: MutableList<() -> Unit> = ArrayList()

    private fun watchLifecycle() {
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
                        Timber.e(it.exception)
                    }
                    else -> Unit
                }
            },
            {
                Timber.e(it)
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
            Timber.i("new subscription: destination=$destination")
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
                Timber.i("send completed")
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
        compositeDisposable = CompositeDisposable()
        watchLifecycle()
        stompClient.connect()
    }

    override fun subscribeTopicConnection(companyId: Int, handler: (ConnectEvent) -> Unit) {
        subscribe("/topic/connection", ConnectEvent::class, handler, {
            Timber.i("received new connection id=" + it.userId + " connect=" + it.connect)
        })
    }

    override fun subscribeQueueMessages(selfId: Int, handler: (Message) -> Unit) {
        subscribe("/user/$selfId/queue/messages", Message::class, handler,
            {
                Timber.i("received new message id=" + it.id + ", text=" + it.text + ", author id=" + it.author.id)
            }
        )
    }

    override fun subscribeQueueChats(selfId: Int, handler: (Chat) -> Unit) {
        subscribe("/user/$selfId/queue/chats", Chat::class, handler,
            {
                Timber.i("received new chat id=" + it.id + ", members=" + it.members.size)
            }
        )
    }

    override fun subscribeQueueReadNotifications(selfId: Int, handler: (ReadNotification) -> Unit) {
        subscribe("/user/$selfId/queue/read_notifications", ReadNotification::class, handler,
            {
                Timber.i("received new read notification chatId=" + it.chatId + " userId=" + it.userId)
            }
        )
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

    override fun disconnect() {
        val disposable = stompClient.disconnectCompletable().subscribe {
            compositeDisposable.dispose()
        }
        compositeDisposable.add(disposable)
    }
}