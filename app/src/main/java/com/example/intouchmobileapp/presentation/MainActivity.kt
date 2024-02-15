package com.example.intouchmobileapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.intouchmobileapp.common.Constants.PARAM_CHAT_ID
import com.example.intouchmobileapp.presentation.Screen.*
import com.example.intouchmobileapp.presentation.chat.ChatScreen
import com.example.intouchmobileapp.presentation.chat_list.ChatListScreen
import com.example.intouchmobileapp.presentation.log_in.LogInScreen
import com.example.intouchmobileapp.presentation.splash_screen.PreLogInScreen
import com.example.intouchmobileapp.presentation.settings.SettingsScreen
import com.example.intouchmobileapp.presentation.splash_screen.SplashScreenViewModel
import com.example.intouchmobileapp.presentation.ui.theme.InTouchMobileAppTheme
import com.example.intouchmobileapp.presentation.user_list.UserListScreen
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
                        composable(SplashScreen.route) {
                            val viewModel: SplashScreenViewModel = hiltViewModel()
                            PreLogInScreen(navController, viewModel::onEvent)
                        }
                        composable(
                            route = LogInScreen.route
                        ) {
                            LogInScreen(navController = navController)
                        }
                        composable(
                            route = ChatListScreen.route
                        ) {
                            ChatListScreen(navController = navController)
                        }
                        composable(
                            route = ChatScreen.route + "/{$PARAM_CHAT_ID}",
                            arguments = listOf(
                                navArgument(PARAM_CHAT_ID) {
                                    type = NavType.IntType
                                }
                            ),
                            enterTransition = {
                                slideIntoContainer(
                                    animationSpec = tween(300, easing = EaseIn),
                                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                                )
                            },
                            exitTransition = {
                                slideOutOfContainer(
                                    animationSpec = tween(300, easing = EaseOut),
                                    towards = AnimatedContentTransitionScope.SlideDirection.End
                                )
                            }
                        ) {
                            ChatScreen(navController = navController)
                        }
                        composable(
                            route = UserListScreen.route
                        ) {
                            UserListScreen(navController = navController)
                        }
                        composable(
                            route = SettingsScreen.route
                        ) {
                            SettingsScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}