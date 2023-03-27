package com.airbot.framework.chatscreen


import androidx.compose.animation.core.*
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbot.data.model.toMessageApi
import com.airbot.domain.model.Chat
import com.airbot.domain.model.MessageAirBot
import com.airbot.ui.theme.Purple700
import com.airbot.utils.UiEvent
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
    channelId: String
) {

    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
            }
        }
    }

    MessageBox(viewModel = viewModel)


}

@Composable
fun LoadingAnimation(
    modifier: Modifier = Modifier,
    circleSize: Dp = 8.dp,
    circleColor: Color = MaterialTheme.colors.primary,
    spaceBetween: Dp = 5.dp,
    travelDistance: Dp = 20.dp
) {
    val circles = listOf(
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) }
    )

    circles.forEachIndexed { index, animatable ->
        LaunchedEffect(key1 = animatable) {
            delay(index * 100L)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1200
                        0.0f at 0 with LinearOutSlowInEasing
                        1.0f at 300 with LinearOutSlowInEasing
                        0.0f at 600 with LinearOutSlowInEasing
                        0.0f at 1200 with LinearOutSlowInEasing
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    val circleValues = circles.map { it.value }
    val distance = with(LocalDensity.current) { travelDistance.toPx() }
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {

        Row(
            modifier = modifier.padding(top = 4.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(spaceBetween)
        ) {
            circleValues.forEach { value ->
                Box(
                    modifier = Modifier
                        .size(circleSize)
                        .graphicsLayer {
                            translationY = -value * distance
                        }
                        .background(
                            color = circleColor,
                            shape = CircleShape
                        )
                )
            }
        }
    }

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

        ChatSection(viewModel, Modifier.weight(1f))
        MessageSection(viewModel)
    }

}

@Composable
fun MessageSection(viewModel: ChatViewModel) {

    val listMessage = viewModel.chatState.collectAsState().value.listMessages

//    listMessage?.let { MessageAirBot("system", it) }?.let { messagesList.add(it) }
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
                        viewModel.listMessage.add(MessageAirBot("user", viewModel.message))
                        viewModel.handleEvent(
                            ChatContract.Event.sendMessage(
                                Chat(
                                    "gpt-3.5-turbo",
                                    viewModel.listMessage.map { messageAirBot -> messageAirBot.toMessageApi() }
                                )
                            )
                        )
                        viewModel.handleEvent(ChatContract.Event.onMessageChange(""))
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
    viewModel: ChatViewModel,
    modifier: Modifier = Modifier
) {
    val simpleDataFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
    val waiting = viewModel.chatState.collectAsState().value.isLoading

    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.End) {

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp), reverseLayout = true
        ) {

            items(viewModel.listMessage.reversed()) { chat ->
                MessageItem(
                    messageAirBot = chat,
                    time = simpleDataFormat.format(chat.time),
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

        }
        if (waiting) {
            LoadingAnimation()
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
            horizontalAlignment = if (messageAirBot.role == "user") Alignment.End else Alignment.Start
        ) {

            if (messageAirBot.content != "") {
                Box(
                    modifier = Modifier
                        .background(
                            if (messageAirBot.role == "user") MaterialTheme.colors.primary else Purple700,
                            shape = if (messageAirBot.role == "user") RoundedCornerShape(
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


val messagesList = mutableListOf<MessageAirBot>()
