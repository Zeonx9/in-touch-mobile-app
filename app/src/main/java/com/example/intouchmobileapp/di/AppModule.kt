package com.example.intouchmobileapp.di

import com.example.intouchmobileapp.common.Constants
import com.example.intouchmobileapp.data.converter.GsonLocalDateAdapter
import com.example.intouchmobileapp.data.converter.GsonLocalDateTimeAdapter
import com.example.intouchmobileapp.data.converter.GsonWSLocalDateAdapter
import com.example.intouchmobileapp.data.converter.GsonWSLocalDateTimeAdapter
import com.example.intouchmobileapp.data.remote.api.AuthApi
import com.example.intouchmobileapp.data.remote.api.ChatApi
import com.example.intouchmobileapp.data.remote.api.MessageApi
import com.example.intouchmobileapp.data.remote.api.StompApi
import com.example.intouchmobileapp.data.remote.api.UserApi
import com.example.intouchmobileapp.data.remote.stomp.StompApiImpl
import com.example.intouchmobileapp.data.repository.ChatRepositoryImpl
import com.example.intouchmobileapp.data.repository.MessageRepositoryImpl
import com.example.intouchmobileapp.data.repository.SelfRepositoryImpl
import com.example.intouchmobileapp.data.repository.UserRepositoryImpl
import com.example.intouchmobileapp.domain.repository.ChatRepository
import com.example.intouchmobileapp.domain.repository.MessageRepository
import com.example.intouchmobileapp.domain.repository.SelfRepository
import com.example.intouchmobileapp.domain.repository.UserRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val gson = GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, GsonLocalDateTimeAdapter())
            .registerTypeAdapter(LocalDate::class.java, GsonLocalDateAdapter())
            .create()
        return Retrofit.Builder()
            .baseUrl(Constants.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideStompClient(): StompClient {
        return Stomp.over(
            Stomp.ConnectionProvider.OKHTTP,
            Constants.WS_ENDPOINT_URL
        )
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun providesChatApi(retrofit: Retrofit): ChatApi {
        return retrofit.create(ChatApi::class.java)
    }

    @Provides
    @Singleton
    fun providesMessageApi(retrofit: Retrofit): MessageApi {
        return retrofit.create(MessageApi::class.java)
    }

    @Provides
    @Singleton
    fun provideStompApi(stompClient: StompClient): StompApi {
        val gson = GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, GsonWSLocalDateTimeAdapter())
            .registerTypeAdapter(LocalDate::class.java, GsonWSLocalDateAdapter())
            .create()
        return StompApiImpl(stompClient, gson)
    }

    @Provides
    @Singleton
    fun providesSelfRepository(): SelfRepository {
        return SelfRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userApi: UserApi,
        selfRepository: SelfRepository
    ): UserRepository {
        return UserRepositoryImpl(userApi, selfRepository)
    }

    @Provides
    @Singleton
    fun provideChatRepository(
        chatApi: ChatApi,
        selfRepository: SelfRepository,
    ): ChatRepository {
        return ChatRepositoryImpl(chatApi, selfRepository)
    }

    @Provides
    @Singleton
    fun provideMessageRepository(
        messageApi: MessageApi,
        selfRepository: SelfRepository
    ): MessageRepository {
        return MessageRepositoryImpl(messageApi, selfRepository)
    }
}