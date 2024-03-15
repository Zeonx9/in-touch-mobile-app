package com.example.intouchmobileapp.di

import android.content.Context
import coil.ImageLoader
import com.example.intouchmobileapp.R
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
import com.example.intouchmobileapp.data.repository.PreferencesRepositoryImpl
import com.example.intouchmobileapp.data.repository.SelfRepositoryImpl
import com.example.intouchmobileapp.data.repository.UserRepositoryImpl
import com.example.intouchmobileapp.domain.repository.ChatRepository
import com.example.intouchmobileapp.domain.repository.MessageRepository
import com.example.intouchmobileapp.domain.repository.PreferencesRepository
import com.example.intouchmobileapp.domain.repository.SelfRepository
import com.example.intouchmobileapp.domain.repository.UserRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    private fun getInTouchTrustManagers(context: Context): Array<out TrustManager> {
        val certificate = CertificateFactory.getInstance("X.509").generateCertificate(
            context.resources.openRawResource(R.raw.intouch_certificate)
        )
        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        keyStore.load(null, null)
        keyStore.setCertificateEntry("intouch", certificate)

        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(keyStore)

        return trustManagerFactory.trustManagers
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val trustManagers = getInTouchTrustManagers(context)
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustManagers, null)

        return OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustManagers[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, GsonLocalDateTimeAdapter())
            .registerTypeAdapter(LocalDate::class.java, GsonLocalDateAdapter())
            .create()

        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(Constants.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideStompClient(httpClient: OkHttpClient): StompClient {
        return Stomp.over(
            Stomp.ConnectionProvider.OKHTTP,
            Constants.WS_ENDPOINT_URL,
            null,
            httpClient
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
    fun providePreferencesRepository(@ApplicationContext context: Context) : PreferencesRepository {
        return PreferencesRepositoryImpl(context)
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

    @Provides
    @Singleton
    fun provideImageLoader(
        okHttpClient: OkHttpClient,
        @ApplicationContext context: Context
    ): ImageLoader {
        return ImageLoader.Builder(context)
            .okHttpClient(okHttpClient)
            .build()
    }
}