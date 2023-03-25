package com.airbot.framework

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.airbot.framework.chatscreen.ChatScreen
import com.airbot.framework.gettokenscreen.GetTokenScreen
import com.airbot.framework.listachats.ListaChatScreen
import com.airbot.framework.perfil.PerfilScreen
import com.airbot.ui.theme.AirBotTheme
import com.airbot.utils.NavigationConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AirBotApp {
                AirBotApp()
            }
        }
    }
}

@Composable
fun AirBotApp(){
    val navController = rememberNavController()

    Scaffold(content = {
        Box(modifier = Modifier.padding(it)) {
            Navigation(navController)
        }
    })
}


@Composable
fun Navigation (navController: NavHostController){

    NavHost(navController = navController, startDestination = NavigationConstants.REGISTER_TOKEN_SCREEN) {
        composable(NavigationConstants.REGISTER_TOKEN_SCREEN){
            GetTokenScreen(
                onNavigate = {navController.navigate(it.route)}
            )
        }
        composable(NavigationConstants.CHAT_SCREEN){
            ChatScreen(
                onNavigate = {navController.navigate(it.route)}
            )
        }
        composable(NavigationConstants.LISTA_CHATS_SCREEN){
            ListaChatScreen(
                onNavigate = {navController.navigate(it.route)}
            )
        }
        composable(NavigationConstants.PERFIL_SCREEN){
            PerfilScreen(
                onNavigate = {navController.navigate(it.route)}
            )
        }
    }


}




