package com.airbot.data.model

import com.airbot.domain.model.APIOpenAI.Message
import com.airbot.domain.model.Chat
import com.airbot.domain.model.MyToken

fun TokenEntity.toMyToken(): MyToken {
    return MyToken(token)
}

fun MyToken.toTokenEntity(): TokenEntity {
    return TokenEntity(1, token)
}