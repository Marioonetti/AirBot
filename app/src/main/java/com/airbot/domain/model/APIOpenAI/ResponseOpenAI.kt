package com.airbot.domain.model.APIOpenAI

data class ResponseOpenAI(
    val choices: List<Choice>,
    val created: Int,
    val id: String,
    val model: String,
    val `object`: String,
    val usage: Usage
)