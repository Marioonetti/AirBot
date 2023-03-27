package com.airbot.framework.chatscreen

import com.airbot.domain.model.APIOpenAI.Message
import com.airbot.domain.model.Chat
import com.airbot.domain.model.MyToken

interface ChatContract {

    sealed class Event{
        data class onMessageChange(val message: String) : Event()



        data class sendMessage(val chat: Chat) : Event()


    }


    data class StateChat(
        val message: MyToken? = null,
        val listMessages: String? = null,
        val isLoading : Boolean = false
    )
}