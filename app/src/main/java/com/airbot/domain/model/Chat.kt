package com.airbot.domain.model

data class
Chat(
    val id: Int,
    val model: String,
    val messages: List<Message>
)
