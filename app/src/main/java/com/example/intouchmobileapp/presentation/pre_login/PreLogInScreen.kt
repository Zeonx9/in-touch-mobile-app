package com.example.intouchmobileapp.presentation.pre_login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.intouchmobileapp.R

@Composable
fun PreLogInScreen(
    preLogInViewModel: PreLogInViewModel = hiltViewModel(),
    navController: NavController
) {
    LaunchedEffect(Unit) {
        preLogInViewModel.tryLogin(navController)
    }

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.intouch_icon),
            contentDescription = "app Icon",
            contentScale = ContentScale.Fit,
            modifier = Modifier.width(64.dp)
        )
        Text(
            text = "In Touch",
            fontSize = 40.sp,
            color = Color.White
        )
        CircularProgressIndicator()
    }
}