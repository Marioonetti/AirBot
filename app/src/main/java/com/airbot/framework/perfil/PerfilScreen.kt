package com.airbot.framework.perfil

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.airbot.utils.UiEvent


@Composable
fun PerfilScreen (onNavigate: (UiEvent.Navigate) -> Unit){

    Text(text = "Pantalla del perfil")

}