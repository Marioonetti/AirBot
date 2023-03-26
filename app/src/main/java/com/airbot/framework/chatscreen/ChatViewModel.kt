package com.airbot.framework.chatscreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airbot.data.repositories.ChatRepository
import com.airbot.data.repositories.LocalRepository
import com.airbot.framework.chatscreen.ChatConstant.ERROR_SEND_MESSAGE
import com.airbot.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
data class ChatViewModel @Inject constructor(
    private var localRepository: LocalRepository,
    private var chatRepository: ChatRepository
) : ViewModel() {


    private val _getChatState: MutableStateFlow<ChatContract.StateChat> by lazy {
        MutableStateFlow(ChatContract.StateChat())
    }
    val getTokenState: StateFlow<ChatContract.StateChat> = _getChatState

    var message by mutableStateOf("")
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
                                    _getChatState.update { it.copy(isLoading = true) }
                                }
                                is NetworkResult.Succcess -> {
                                    _getChatState.update {
                                        it.copy(listMessages = result.data?.choices?.map{ choice -> choice.message }
                                            ?.map { message -> message.content }?.get(0))
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
}