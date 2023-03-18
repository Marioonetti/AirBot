package com.airbot.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tokenEntity")
data class TokenEntity(
    @PrimaryKey(autoGenerate = true)
    var tokenid: Int,
    var token: String
)
