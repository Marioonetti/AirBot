package com.airbot.framework.chatscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbot.domain.model.Chat
import com.airbot.domain.model.MessageAirBot
import com.airbot.ui.theme.Purple700
import com.airbot.utils.UiEvent
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
    channelId: String
) {

    MessageBox(viewModel = viewModel)


}

@Composable
fun MessageBox(viewModel: ChatViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopBarSection(
            username = "Bot",
            profile = painterResource(id = com.airbot.R.drawable.ic_launcher_background)
        )
        ChatSection(Modifier.weight(1f))
        MessageSection(viewModel)
    }

}

@Composable
fun MessageSection(viewModel: ChatViewModel) {
    val context = LocalContext.current
    val listMessage = viewModel.getTokenState.collectAsState().value.listMessages

    listMessage?.let { MessageAirBot("system", it) }?.let { messagesList.add(it) }
    Card(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = Color.White,
        elevation = 10.dp
    ) {

        OutlinedTextField(
            placeholder = {
                Text(text = "Message..")
            },
            value = viewModel.message,
            onValueChange = {
                viewModel.handleEvent(ChatContract.Event.onMessageChange(it))
            },
            shape = RoundedCornerShape(25.dp),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = io.getstream.chat.android.compose.R.drawable.stream_compose_ic_send),
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.clickable {
                        messagesList.add(MessageAirBot("user", viewModel.message))
                        viewModel.handleEvent(
                            ChatContract.Event.sendMessage(
                                Chat(
                                    "gpt-3.5-turbo",
                                    messagesList
                                )
                            )
                        )
                    }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)

        )
    }
}

@Composable
fun TopBarSection(
    username: String,
    profile: Painter
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        backgroundColor = Color(color = 0xFFFAFAFA),
        elevation = 4.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            Image(
                painter = profile,
                contentDescription = "Image people",
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(verticalArrangement = Arrangement.Center) {
                Text(text = username, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            }

        }
    }
}


@Composable
fun ChatSection(
    modifier: Modifier = Modifier
) {
    val simpleDataFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp), reverseLayout = true
    ) {
        items(messagesList) { chat ->
            MessageItem(
                messageAirBot = chat,
                time = simpleDataFormat.format(chat.time)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Composable
fun MessageItem(
    messageAirBot: MessageAirBot?,
    time: String
) {
    if (messageAirBot != null) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = if (messageAirBot.role == "User") Alignment.End else Alignment.Start
        ) {

            if (messageAirBot.content != "") {
                Box(
                    modifier = Modifier
                        .background(
                            if (messageAirBot.role == "User") MaterialTheme.colors.primary else Purple700,
                            shape = if (messageAirBot.role == "User") RoundedCornerShape(
                                8.dp,
                                0.dp,
                                8.dp,
                                8.dp
                            ) else RoundedCornerShape(0.dp, 8.dp, 8.dp, 8.dp)
                        )
                        .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
                ) {
                    Text(text = messageAirBot.content, color = Color.White)
                }
            }
            Text(text = time, fontSize = 12.sp, modifier = Modifier.padding(start = 8.dp))
        }

    }
}


val messages_prueba = listOf(
    MessageAirBot(role = "System", content = "Bien"),
    MessageAirBot(role = "User", content = "¿Que tal estas?"),
    MessageAirBot(role = "System", content = "Hola!"),
    MessageAirBot(role = "User", content = "Buenas")

)

val messagesList = mutableListOf<MessageAirBot>()
