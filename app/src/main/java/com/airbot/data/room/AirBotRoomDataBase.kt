package com.airbot.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.airbot.data.model.TokenEntity
import com.airbot.data.room.dao.AirBotDao


@Database(
    entities = [TokenEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AirBotRoomDataBase: RoomDatabase() {
    abstract fun airbotDao(): AirBotDao
}
