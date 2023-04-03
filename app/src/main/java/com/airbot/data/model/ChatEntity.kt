package com.airbot.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.airbot.domain.model.Message

@Entity(tableName = "chatEntity")
data class ChatEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var model: String
        )