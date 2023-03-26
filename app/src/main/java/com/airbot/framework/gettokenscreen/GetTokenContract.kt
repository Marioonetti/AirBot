package com.airbot.framework.gettokenscreen

import com.airbot.domain.model.MyToken

interface GetTokenContract {

    sealed class Event{
        object insertToken : Event()
        data class onTokenChange(val token: String) : Event()

        object clearToken : Event()
    }


    data class StateGetToken(
        val token: MyToken? = null
    )
}