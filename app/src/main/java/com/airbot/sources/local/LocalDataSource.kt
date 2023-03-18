package com.airbot.sources.local


import com.airbot.data.model.TokenEntity
import com.airbot.data.room.dao.AirBotDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val airBotDao: AirBotDao
) {

    fun getToken(): Flow<String> = airBotDao.getToken()

    fun insertToken(tokenEntity: TokenEntity) = airBotDao.insertToken(tokenEntity)
}