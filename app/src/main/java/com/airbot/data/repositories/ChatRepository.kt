package com.airbot.data.repositories

import com.airbot.data.model.toChat
import com.airbot.data.model.toChatWithMessages
import com.airbot.domain.model.Chat
import com.airbot.sources.local.ChatDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val chatDataSource: ChatDataSource
) {

    fun getAllChats(): Flow<List<Chat>>{
        return chatDataSource.getAllChats().map { it -> it.map { chatWithMessages ->  chatWithMessages.toChat() } }
            .flowOn(Dispatchers.IO)
    }
    
    fun insertChat(chat: Chat){
        chatDataSource.insertChat(chat.toChatWithMessages())
    }

}