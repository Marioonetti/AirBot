package com.airbot.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.airbot.data.model.ChatEntity
import com.airbot.data.model.ChatWithMessages
import kotlinx.coroutines.flow.Flow


@Dao
interface ChatDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addConversation(chat: ChatWithMessages)

    @Transaction
    @Query("select * from chatEntity")
    fun getAllConversations() : Flow<List<ChatWithMessages>>

}
