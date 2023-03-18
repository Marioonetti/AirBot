package com.airbot.sources.di

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    private val cache: CacheTokenOpenAI
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        var request: Request
        request = if (cache.token != "" ) {
            //si no lo tenemos cacheado vamos a bbdd y lo cogemos de ahi
            original.newBuilder()
                .header(
                    ConstantesNetwork.AUTHORIZATION,
                    ""
                ).build()

        } else {
            //en caso de estar cacheado lo cogemos de la cache
            original.newBuilder()
                .header(
                    ConstantesNetwork.AUTHORIZATION,
                    cache.token
                ).build()
        }

        var response = chain.proceed(request)

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
        }*/
        return response
    }
}