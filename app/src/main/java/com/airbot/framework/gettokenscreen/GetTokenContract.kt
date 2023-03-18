package com.airbot.framework.gettokenscreen

import com.airbot.domain.MyToken

interface GetTokenContract {

    sealed class Event{
        object insertToken : Event()
        data class onTokenChange(val token: String) : Event()
    }


    data class StateGetToken(
        val token: MyToken? = null
    )
}