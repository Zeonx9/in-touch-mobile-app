package com.example.intouchmobileapp.di

import com.example.intouchmobileapp.common.Constants
import com.example.intouchmobileapp.data.remote.api.AuthApi
import com.example.intouchmobileapp.data.remote.api.ChatApi
import com.example.intouchmobileapp.data.remote.api.UserApi
import com.example.intouchmobileapp.data.repository.ChatRepositoryImpl
import com.example.intouchmobileapp.data.repository.SelfRepositoryImpl
import com.example.intouchmobileapp.data.repository.UserRepositoryImpl
import com.example.intouchmobileapp.domain.repository.ChatRepository
import com.example.intouchmobileapp.domain.repository.SelfRepository
import com.example.intouchmobileapp.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
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
    fun providesSelfRepository(): SelfRepository {
        return SelfRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideUserRepository(userApi: UserApi, selfRepository: SelfRepository): UserRepository {
        return UserRepositoryImpl(userApi, selfRepository)
    }

    @Provides
    @Singleton
    fun provideChatRepository(chatApi: ChatApi, selfRepository: SelfRepository): ChatRepository {
        return ChatRepositoryImpl(chatApi, selfRepository)
    }
}