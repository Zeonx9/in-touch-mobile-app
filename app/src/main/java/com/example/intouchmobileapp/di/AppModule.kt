package com.example.intouchmobileapp.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.intouchmobileapp.common.Constants
import com.example.intouchmobileapp.data.converter.GsonLocalDateTimeAdapter
import com.example.intouchmobileapp.data.remote.api.AuthApi
import com.example.intouchmobileapp.data.remote.api.ChatApi
import com.example.intouchmobileapp.data.remote.api.StompApi
import com.example.intouchmobileapp.data.remote.api.UserApi
import com.example.intouchmobileapp.data.remote.stomp.StompApiImpl
import com.example.intouchmobileapp.data.repository.ChatRepositoryImpl
import com.example.intouchmobileapp.data.repository.SelfRepositoryImpl
import com.example.intouchmobileapp.data.repository.UserRepositoryImpl
import com.example.intouchmobileapp.domain.repository.ChatRepository
import com.example.intouchmobileapp.domain.repository.SelfRepository
import com.example.intouchmobileapp.domain.repository.UserRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import java.time.LocalDateTime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, GsonLocalDateTimeAdapter())
            .create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit {
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
    fun provideStompApi(
        stompClient: StompClient,
        gson: Gson
    ): StompApi {
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
}