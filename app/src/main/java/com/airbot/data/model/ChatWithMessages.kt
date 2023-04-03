package com.airbot.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

data class ChatWithMessages (
    @Embedded val chat :ChatEntity,
    @Relation(
            parentColumn = "id",
            entityColumn = "chatId"
            )
    val messages: List<MessageEntity>
        )