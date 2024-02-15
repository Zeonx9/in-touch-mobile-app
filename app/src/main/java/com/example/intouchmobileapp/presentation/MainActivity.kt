package com.example.intouchmobileapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.intouchmobileapp.presentation.Screen.SplashScreen
import com.example.intouchmobileapp.presentation.chat.chatScreenComposable
import com.example.intouchmobileapp.presentation.chat_list.chatListScreenComposable
import com.example.intouchmobileapp.presentation.log_in.logInScreenComposable
import com.example.intouchmobileapp.presentation.settings.settingsComposable
import com.example.intouchmobileapp.presentation.splash_screen.splashScreenComposable
import com.example.intouchmobileapp.presentation.ui.theme.InTouchMobileAppTheme
import com.example.intouchmobileapp.presentation.user_list.userListComposable
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
                        startDestination = SplashScreen.route
                    ) {
                        splashScreenComposable(navController)
                        logInScreenComposable(navController)
                        chatListScreenComposable(navController)
                        chatScreenComposable(navController)
                        userListComposable(navController)
                        settingsComposable(navController)
                    }
                }
            }
        }
    }
}