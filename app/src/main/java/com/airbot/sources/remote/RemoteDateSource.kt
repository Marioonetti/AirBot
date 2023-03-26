package com.airbot.sources.remote

import com.airbot.domain.model.Chat
import com.airbot.sources.BaseApiResponse
import javax.inject.Inject

class RemoteDateSource @Inject constructor(
    private val chatService: ChatService
): BaseApiResponse(){

    suspend fun sendMessage(chat: Chat) = safeApiCall(apicall = { chatService.sendMessage(chat)})
}