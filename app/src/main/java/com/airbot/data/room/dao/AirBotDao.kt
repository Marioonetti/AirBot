package com.airbot.data.room.dao

import androidx.room.*
import com.airbot.data.model.TokenEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AirBotDao {

    @Transaction
    @Query("select token from tokenEntity where tokenid = 1")
    fun getToken(): Flow<String>
    @Query("select count(*) from tokenEntity")
    fun checkExistToken() : Int
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToken(token: TokenEntity)

    @Update
    suspend fun updateToken(token: TokenEntity)

    @Query("DELETE FROM tokenEntity")
    suspend fun clearTokens()

}
