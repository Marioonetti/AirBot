package com.airbot.sources.local


import com.airbot.data.model.TokenEntity
import com.airbot.data.room.dao.AirBotDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TokenDataSource @Inject constructor(
    private val airBotDao: AirBotDao
) {

    fun getToken(): Flow<String> = airBotDao.getToken()

    fun checkExistToken() : Int  = airBotDao.checkExistToken()

    fun insertToken(tokenEntity: TokenEntity) = airBotDao.insertToken(tokenEntity)

    suspend fun clearTokens() = airBotDao.clearTokens()
}