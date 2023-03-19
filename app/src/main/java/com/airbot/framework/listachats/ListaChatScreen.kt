package com.airbot.framework.listachats

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.airbot.utils.UiEvent


@Composable
fun ListaChatScreen(onNavigate: (UiEvent.Navigate) -> Unit) {

    Row {
        Text(text = "Lista chat")

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Perfil")
        }

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Chat")
        }
    }
}