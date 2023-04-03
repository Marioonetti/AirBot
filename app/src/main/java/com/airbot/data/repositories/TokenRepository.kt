package com.airbot.data.repositories

import com.airbot.data.model.toTokenEntity
import com.airbot.domain.model.MyToken
import com.airbot.sources.local.TokenDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TokenRepository @Inject constructor(
    private val tokenDataSource: TokenDataSource
){


    fun getToken(): Flow<String> {
        return tokenDataSource.getToken().flowOn(Dispatchers.IO)
    }

    fun checkExistToken(): Int = tokenDataSource.checkExistToken()

    fun insertToken(token: MyToken) {
        tokenDataSource.insertToken(token.toTokenEntity())
    }

    suspend fun clearToken() {
        tokenDataSource.clearTokens()
    }
}