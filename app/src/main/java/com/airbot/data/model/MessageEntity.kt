package com.airbot.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messageEntity")
data class MessageEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val chatId: Int,
    val role: String,
    val content: String
        )