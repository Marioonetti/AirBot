package com.airbot.framework.gettokenscreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airbot.data.repositories.LocalRepository
import com.airbot.domain.model.MyToken
import com.airbot.utils.NavigationConstants
import com.airbot.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
data class GetTokenViewModel @Inject constructor(
    private val localRepository: LocalRepository
) : ViewModel(){


    private val _getTokenState : MutableStateFlow<GetTokenContract.StateGetToken> by lazy {
        MutableStateFlow(GetTokenContract.StateGetToken())
    }
    val getTokenState : StateFlow<GetTokenContract.StateGetToken> = _getTokenState


    private val _uiEvent = Channel<UiEvent> ()
    val uiEvent = _uiEvent.receiveAsFlow()

    var token by mutableStateOf("")
        private set

    @SuppressLint("LongLogTag")
    fun handleEvent(event: GetTokenContract.Event){
        when(event){
            is GetTokenContract.Event.insertToken -> {
                viewModelScope.launch {
                    try {
                        localRepository.insertToken(MyToken(token))
                        sendUiEvent(UiEvent.Navigate(NavigationConstants.LISTA_CHATS_SCREEN))
                    }catch (e: Exception){
                        e.message?.let { Log.e("Error al insertar el token", it) }
                    }
                }
            }

            is GetTokenContract.Event.onTokenChange -> {
                token = event.token
            }

        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}




