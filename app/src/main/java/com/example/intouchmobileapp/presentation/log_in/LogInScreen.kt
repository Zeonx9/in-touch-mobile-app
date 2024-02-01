package com.example.intouchmobileapp.presentation.log_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun LogInScreen (
    navController: NavController,
    logInViewModel: LogInViewModel = hiltViewModel()
) {
    val state = logInViewModel.state.value

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = state.login,
            onValueChange = {
                logInViewModel.updateLogin(it)
            },
            label = {
                Text("login")
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = state.password,
            onValueChange = {
                logInViewModel.updatePassword(it)
            },
            label = {
                Text(text = "password")
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                logInViewModel.logIn(navController)
            }
        ) {
            Text(text = "log in")
        }
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = Color.Red,
                modifier = Modifier.padding(20.dp)
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .width(64.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}