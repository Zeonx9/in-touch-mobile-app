package com.example.intouchmobileapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.intouchmobileapp.presentation.chat_list.ChatListScreen
import com.example.intouchmobileapp.presentation.log_in.LogInScreen
import com.example.intouchmobileapp.presentation.ui.theme.InTouchMobileAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InTouchMobileAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.LogInScreen.route
                    ) {
                        composable(Screen.LogInScreen.route) {
                            LogInScreen(navController = navController)
                        }
                        composable(Screen.ChatListScreen.route) {
                            ChatListScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}