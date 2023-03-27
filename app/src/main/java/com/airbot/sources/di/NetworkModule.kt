package com.airbot.sources.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.airbot.data.repositories.LocalRepository
import com.airbot.sources.remote.ChatService
import com.airbot.utils.Constants
import com.google.gson.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(cacheTokenOpenAI: CacheTokenOpenAI,localRepository: LocalRepository): OkHttpClient {
        val authorizationInterceptor = AuthorizationInterceptor(cacheTokenOpenAI,localRepository)
        return OkHttpClient
            .Builder()
            .protocols(listOf(Protocol.HTTP_1_1))
            .readTimeout(15, TimeUnit.MINUTES)
            .connectTimeout(15, TimeUnit.MINUTES)
            .addInterceptor(authorizationInterceptor)
            .build()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @Singleton
    @Provides
    fun getGson(): Gson {
        return GsonBuilder().registerTypeAdapter(
            LocalDate::class.java,
            JsonDeserializer { jsonElement: JsonElement, type: Type?, jsonDeserializationContext: JsonDeserializationContext? ->
                LocalDate.parse(
                    jsonElement.asJsonPrimitive.asString
                )
            } as JsonDeserializer<LocalDate>)
            .registerTypeAdapter(
                LocalDate::class.java,
                JsonSerializer { localDate: LocalDate, type: Type?, jsonSerializationContext: JsonSerializationContext? ->
                    JsonPrimitive(
                        localDate.toString()
                    )
                } as JsonSerializer<LocalDate>)
            .registerTypeAdapter(
                LocalDateTime::class.java,
                JsonDeserializer { jsonElement: JsonElement, type: Type?, jsonDeserializationContext: JsonDeserializationContext? ->
                    LocalDateTime.parse(
                        jsonElement.asJsonPrimitive.asString
                    )
                } as JsonDeserializer<LocalDateTime>)
            .registerTypeAdapter(
                LocalDateTime::class.java,
                JsonSerializer { localDate: LocalDateTime, type: Type?, jsonSerializationContext: JsonSerializationContext? ->
                    JsonPrimitive(
                        localDate.toString()
                    )
                } as JsonSerializer<LocalDateTime>)
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun getCacheDataUser(): CacheTokenOpenAI {
        val cacheDataUser = CacheTokenOpenAI()
        return cacheDataUser
    }

    @Singleton
    @Provides
    fun chatService(retrofit: Retrofit): ChatService = retrofit.create(ChatService::class.java)


}