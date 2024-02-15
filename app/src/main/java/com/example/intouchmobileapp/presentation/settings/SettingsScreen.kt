package com.example.intouchmobileapp.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.intouchmobileapp.R
import com.example.intouchmobileapp.presentation.Screen
import com.example.intouchmobileapp.presentation.common.BottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    onEvent: (SettingsScreenEvent) -> Unit
) {
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.settings_screen_title)) }
            )
        },
        bottomBar = { BottomNavBar(navController = navController) }
    ) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Button(onClick = { onEvent(SettingsScreenEvent.LogOutClickedEvent(navController)) }) {
                Text(text = stringResource(R.string.log_out_button_text))
            }
        }
    }
}

fun NavGraphBuilder.settingsComposable(navController: NavController) {
    composable(
        route = Screen.SettingsScreen.route
    ) {
        val viewModel: SettingsViewModel = hiltViewModel()
        SettingsScreen(navController, viewModel::onEvent)
    }
}