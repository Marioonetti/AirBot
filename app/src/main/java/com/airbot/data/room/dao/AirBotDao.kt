package com.airbot.data.room.dao

import androidx.room.*
import com.airbot.data.model.TokenEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AirBotDao {

    @Transaction
    @Query("select token from tokenEntity where tokenid = 1")
    fun getToken(): Flow<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertToken(token: TokenEntity)

    @Update
    suspend fun updateToken(token: TokenEntity)

}
