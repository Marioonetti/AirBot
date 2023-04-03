package com.airbot.framework.listachats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airbot.data.model.toChatWithMessages
import com.airbot.data.repositories.ChatRepository
import com.airbot.sources.local.ChatDataSource
import com.airbot.utils.NavigationConstants
import com.airbot.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
data class ListaChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository): ViewModel(){

    private val _uiEvent = Channel<UiEvent> ()
    val uiEvent = _uiEvent.receiveAsFlow()
    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    fun handleEvent(event: ListaChatContract.Event){
        when(event){
            is ListaChatContract.Event.navToChat -> {
                sendUiEvent(UiEvent.Navigate(NavigationConstants.CHAT_SCREEN))
            }
            is ListaChatContract.Event.navToPerfil -> {
                sendUiEvent(UiEvent.Navigate(NavigationConstants.PERFIL_SCREEN))
            }
            is ListaChatContract.Event.addNewChat -> {
                chatRepository.insertChat(event.chat)
            }



        }

    }


}