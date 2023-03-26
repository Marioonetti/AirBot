package com.airbot.domain.model.APIOpenAI

data class Choice(
    val finish_reason: String,
    val index: Int,
    val message: Message
)