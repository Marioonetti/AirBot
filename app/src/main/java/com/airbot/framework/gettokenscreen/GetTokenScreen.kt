package com.airbot.framework.gettokenscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbot.utils.UiEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GetTokenScreen(
    viewModel: GetTokenViewModel = hiltViewModel(),
    /*onNavigate: (UiEvent.Navigate) -> Unit*/
) {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> TODO()
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


    Scaffold(scaffoldState = scaffoldState) {


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = MaterialTheme.colors.primary),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                OutlinedTextField(
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = White,
                    ),
                    placeholder = { Text(text = "Inserta el token") },
                    value = viewModel.token,
                    onValueChange = {
                        viewModel.handleEvent(GetTokenContract.Event.onTokenChange(it))
                    })

                Spacer(modifier = Modifier.size(30.dp))

                OutlinedButton(
                    modifier = Modifier
                        .width(135.dp)
                        .size(50.dp)
                        .border(width = 1.dp, color = White, shape = RoundedCornerShape(8.dp)),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 6.dp,
                        pressedElevation = 8.dp,
                        disabledElevation = 0.dp
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = Blue
                    ),
                    onClick = {
                        viewModel.handleEvent(GetTokenContract.Event.insertToken)
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Token insertado ")
                        }
                    },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Registrarse", color = White)
                }
            }
        }

    }

}
