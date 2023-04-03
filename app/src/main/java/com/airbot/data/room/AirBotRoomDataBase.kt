package com.airbot.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.airbot.data.model.ChatEntity
import com.airbot.data.model.ChatWithMessages
import com.airbot.data.model.MessageEntity
import com.airbot.data.model.TokenEntity
import com.airbot.data.room.dao.AirBotDao
import com.airbot.data.room.dao.ChatDao


@Database(
    entities = [TokenEntity::class, ChatEntity::class, MessageEntity::class, ChatWithMessages::class],
    version = 3,
    exportSchema = true
)
abstract class AirBotRoomDataBase: RoomDatabase() {
    abstract fun airbotDao(): AirBotDao

    abstract fun chatDao(): ChatDao
}
