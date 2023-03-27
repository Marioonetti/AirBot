package com.airbot.framework.chatscreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airbot.data.repositories.ChatRepository
import com.airbot.data.repositories.LocalRepository
import com.airbot.domain.model.MessageAirBot
import com.airbot.framework.chatscreen.ChatConstant.ERROR_SEND_MESSAGE
import com.airbot.utils.NetworkResult
import com.airbot.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
data class ChatViewModel @Inject constructor(
    private var localRepository: LocalRepository,
    private var chatRepository: ChatRepository
) : ViewModel() {


    private val _chatState: MutableStateFlow<ChatContract.StateChat> by lazy {
        MutableStateFlow(ChatContract.StateChat())
    }
    val chatState: StateFlow<ChatContract.StateChat> = _chatState

    private val _uiEvent = Channel<UiEvent> ()
    val uiEvent = _uiEvent.receiveAsFlow()

    var message by mutableStateOf("")
        private set

    var listMessage =  mutableStateListOf<MessageAirBot>()
        private set
    @SuppressLint("LongLogTag")
    fun handleEvent(event: ChatContract.Event) {
        when (event) {
            is ChatContract.Event.onMessageChange -> {
                message = event.message
            }
            is ChatContract.Event.sendMessage -> {
                viewModelScope.launch {
                    try {
                        chatRepository.sendMessage(event.chat).catch(action = { cause ->
                            Log.e(
                                ERROR_SEND_MESSAGE,
                                cause.message ?: ERROR_SEND_MESSAGE
                            )
                        }).collect { result ->
                            when (result) {

                                is NetworkResult.Loading -> {
                                    _chatState.update { it.copy(isLoading = true) }
                                }
                                is NetworkResult.Succcess -> {
                                    _chatState.update { it.copy(isLoading = false) }
                                    result.data?.choices?.map{ choice -> choice.message }
                                        ?.map { message -> message.content }?.get(0)
                                        ?.let { listMessage.add(MessageAirBot("system",it)) }

                                    /*_getChatState.update {
                                        it.copy(listMessages = result.data?.choices?.map{ choice -> choice.message }
                                            ?.map { message -> message.content }?.get(0))
                                    }*/
                                }
                                is NetworkResult.Error -> {
                                    result.message?.let { mensaje ->
                                        sendUiEvent(
                                            UiEvent.ShowSnackbar(
                                                message = mensaje
                                            )
                                        )
                                    }
                                }
                            }

                        }
                    } catch (e: Exception) {
                        e.message?.let { Log.e(ERROR_SEND_MESSAGE, it) }
                    }
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}