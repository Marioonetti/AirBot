package com.airbot.framework.listachats

import com.airbot.domain.model.Chat

interface ListaChatContract {

    sealed class Event{
        object navToPerfil : Event()
        object navToChat : Event()

        data class addNewChat(val chat: Chat) : Event()

        object getAllChats: Event()
    }


}