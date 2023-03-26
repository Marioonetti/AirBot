package com.airbot.sources.remote

import com.airbot.domain.model.APIOpenAI.ResponseOpenAI
import com.airbot.domain.model.Chat
import retrofit2.Response
import retrofit2.http.POST

interface ChatService {

    @POST("chat/completions")
    suspend fun sendMessage(chat: Chat) : Response<ResponseOpenAI>
}