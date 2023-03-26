package com.airbot.data.repositories

import com.airbot.data.model.toTokenEntity
import com.airbot.domain.model.MyToken
import com.airbot.sources.local.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val localDataSource: LocalDataSource
){


    fun getToken(): Flow<String> {
        return localDataSource.getToken().flowOn(Dispatchers.IO)
    }

    fun checkExistToken(): Int = localDataSource.checkExistToken()

    fun insertToken(token: MyToken) {
        localDataSource.insertToken(token.toTokenEntity())
    }

    suspend fun clearToken() {
        localDataSource.clearTokens()
    }
}