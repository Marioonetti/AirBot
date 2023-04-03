package com.airbot.data.model

import com.airbot.domain.model.Chat
import com.airbot.domain.model.Message
import com.airbot.domain.model.MyToken

fun TokenEntity.toMyToken(): MyToken {
    return MyToken(token)
}

fun MyToken.toTokenEntity(): TokenEntity {
    return TokenEntity(1, token)
}

fun Chat.toChatWithMessages(): ChatWithMessages{
    return ChatWithMessages(
        ChatEntity(id, model),
        messages.map { message -> message.toMessageEntity(id)}
    )
}
fun Message.toMessageEntity(chatId: Int): MessageEntity{
    return MessageEntity(0, chatId, role, content)
}

fun ChatWithMessages.toChat(): Chat {
    return Chat(
        chat.id, chat.model,
        messages = messages.map { messageEntity -> messageEntity.toMessage() }
    )
}

fun MessageEntity.toMessage(): Message {
    return Message(role, content)
}