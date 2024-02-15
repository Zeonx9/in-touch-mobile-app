package com.example.intouchmobileapp.presentation.splash_screen

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.intouchmobileapp.R
import com.example.intouchmobileapp.presentation.Screen

@Composable
fun SplashScreen(
    navController: NavController,
    onEvent: (SplashScreenEvent) -> Unit
) {
    LaunchedEffect(Unit) {
        onEvent(SplashScreenEvent.LogInEvent(navController))
    }

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.intouch_icon),
            contentDescription = stringResource(R.string.app_icon_description),
            contentScale = ContentScale.Fit,
            modifier = Modifier.width(72.dp)
        )
        CircularProgressIndicator()
    }
}

fun NavGraphBuilder.splashScreenComposable(navController: NavController) {
    composable(
        route = Screen.SplashScreen.route,
        exitTransition = {
            slideOutOfContainer(
                animationSpec = tween(300, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        }
    ) {
        val viewModel: SplashScreenViewModel = hiltViewModel()
        SplashScreen(navController, viewModel::onEvent)
    }
}