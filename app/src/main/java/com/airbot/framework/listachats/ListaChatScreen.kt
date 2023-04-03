package com.airbot.framework.listachats

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbot.domain.model.Chat
import com.airbot.domain.model.Message
import com.airbot.utils.UiEvent
import kotlinx.coroutines.flow.collect


@Composable
fun ListaChatScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ListaChatViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val messages = mutableListOf<Message>(Message("system", "Hola"), Message("user", "Que tal?"))

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.OnBackNavigate -> TODO()
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
            }
        }
    }

    Row {
        Text(text = "Lista chat")

        Button(onClick = { viewModel.handleEvent(ListaChatContract.Event.navToPerfil) }) {
            Text(text = "Perfil")
        }



        Button(onClick = { viewModel.handleEvent(ListaChatContract.Event.addNewChat(Chat(id = 0, model = "test", messages = messages))) }) {
            Text(text = "Chat")
        }
    }
}