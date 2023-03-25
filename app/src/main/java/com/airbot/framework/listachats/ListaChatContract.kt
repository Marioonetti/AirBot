package com.airbot.framework.listachats

interface ListaChatContract {

    sealed class Event{
        object navToPerfil : Event()
        object navToChat : Event()
    }


}