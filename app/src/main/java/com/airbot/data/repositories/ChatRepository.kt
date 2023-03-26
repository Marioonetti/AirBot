package com.airbot.data.repositories

import com.airbot.domain.model.APIOpenAI.ResponseOpenAI
import com.airbot.domain.model.Chat
import com.airbot.sources.remote.RemoteDateSource
import com.airbot.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val remoteDateSource: RemoteDateSource
) {


    suspend fun sendMessage(chat: Chat): Flow<NetworkResult<ResponseOpenAI>> {
        return flow {
            emit(NetworkResult.Loading())
            emit(remoteDateSource.sendMessage(chat))

        }.flowOn(Dispatchers.IO)
    }
}