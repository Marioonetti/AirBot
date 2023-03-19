package com.airbot.framework

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.airbot.framework.gettokenscreen.GetTokenScreen
import com.airbot.ui.theme.AirBotTheme

@Composable
fun AirBotApp(content: @Composable () -> Unit) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        content()
    }


}