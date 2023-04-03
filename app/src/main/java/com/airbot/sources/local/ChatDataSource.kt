package com.airbot.sources.local

import com.airbot.data.model.ChatEntity
import com.airbot.data.model.ChatWithMessages
import com.airbot.data.room.dao.ChatDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatDataSource @Inject constructor(
    private val chatDao: ChatDao
) {

    fun getAllChats(): Flow<List<ChatWithMessages>> = chatDao.getAllConversations()

    fun insertChat(chat: ChatWithMessages) = chatDao.addConversation(chat)


}