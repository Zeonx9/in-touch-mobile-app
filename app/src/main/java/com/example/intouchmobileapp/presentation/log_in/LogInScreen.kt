package com.example.intouchmobileapp.presentation.log_in

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.intouchmobileapp.R
import com.example.intouchmobileapp.presentation.Screen
import com.example.intouchmobileapp.presentation.log_in.LogInScreenEvent.LogInClickEvent
import com.example.intouchmobileapp.presentation.log_in.LogInScreenEvent.LoginTextChangedEvent
import com.example.intouchmobileapp.presentation.log_in.LogInScreenEvent.PasswordTextChanged

@Composable
fun LogInScreen (
    navController: NavController,
    state: LogInState,
    onEvent: (LogInScreenEvent) -> Unit,
) {
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = state.login,
            onValueChange = { onEvent(LoginTextChangedEvent(it)) },
            label = { Text(stringResource(R.string.login_text_field_label)) }
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = state.password,
            onValueChange = {onEvent(PasswordTextChanged(it)) },
            label = { Text(stringResource(R.string.password_text_field_label)) }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = { onEvent(LogInClickEvent(navController)) }
        ) {
            Text(stringResource(R.string.log_in_button_text))
        }
        Spacer(modifier = Modifier.height(10.dp))

        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = Color.Red,
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator()
        }
    }
}

fun NavGraphBuilder.logInScreenComposable(navController: NavController) {
    composable(
        route = Screen.LogInScreen.route,
        enterTransition = {
            slideIntoContainer(
                animationSpec = tween(300, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        },
        exitTransition = {
            slideOutOfContainer(
                animationSpec = tween(300, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        }
    ) {
        val viewModel: LogInViewModel = hiltViewModel()
        LogInScreen(navController, viewModel.state.value, viewModel::onEvent)
    }
}