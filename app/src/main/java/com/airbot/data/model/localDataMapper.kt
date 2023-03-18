package com.airbot.data.model

import com.airbot.domain.MyToken

fun TokenEntity.toMyToken(): MyToken {
    return MyToken(token)
}

fun MyToken.toTokenEntity(): TokenEntity {
    return TokenEntity(0, token)
}