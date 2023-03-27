package com.airbot.sources.di

import com.airbot.data.repositories.LocalRepository
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    private val cache: CacheTokenOpenAI,
    private val localRepository: LocalRepository

) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        /*val url: HttpUrl
        val request: Request
        if (cache.token != "") {
            url = chain.request().url.newBuilder()
                .addQueryParameter(
                    ConstantesNetwork.AUTHORIZATION,
                    ConstantesNetwork.BEARER + cache.token
                )
                .build()
            request = chain.request().newBuilder()
                .url(url)
                .build()
        } else {
            url = chain.request().url.newBuilder()
                .addQueryParameter(
                    ConstantesNetwork.AUTHORIZATION,
                    ConstantesNetwork.BEARER + localRepository.getToken()
                )
                .build()
            request = chain.request().newBuilder()
                .url(url)
                .build()
        }

        return chain.proceed(request)*/
        val original = chain.request()
        val request: Request = if (cache.token != "") {
            //si no lo tenemos cacheado vamos a bbdd y lo cogemos de ahi
            original.newBuilder()
                .header(
                    ConstantesNetwork.AUTHORIZATION,
                    ConstantesNetwork.BEARER + cache.token
                ).build()

        } else {
            //en caso de estar cacheado lo cogemos de la cache
            original.newBuilder()
                .header(
                    ConstantesNetwork.AUTHORIZATION,
                    ConstantesNetwork.BEARER + localRepository.getToken()
                ).build()
        }

        return chain.proceed(request);
        // Tenemos que comprobar si el token expira que es lo que ocurre y en caso de que sea asi volver a pedirlo
        /*if (!response.isSuccessful && Objects.equals(
                response.header(EXPIRES),
                TOKEN_EXPIRADO
            )
        ) {
            response.close()
            request = original.newBuilder().header(
                AUTHORIZATION,
                Credentials.basic(cache.email, cache.pass)
            ).build()
            response = chain.proceed(request)
            if (response.header(AUTHORIZATION) != null) {
                cache.token = response.header(AUTHORIZATION)
            }
        }
        return response*/
    }
}