package com.example.intouchmobileapp.presentation.splash_screen

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
import androidx.navigation.NavController
import com.example.intouchmobileapp.R

@Composable
fun PreLogInScreen(
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
            modifier = Modifier.width(64.dp)
        )
        CircularProgressIndicator()
    }
}