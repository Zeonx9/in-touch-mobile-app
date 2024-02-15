package com.example.intouchmobileapp.presentation.chat_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import com.example.intouchmobileapp.presentation.chat_list.components.ChatListItem
import com.example.intouchmobileapp.presentation.common.BottomNavBar
import com.example.intouchmobileapp.presentation.common.LoadingErrorPlaceHolder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    navController: NavController,
    state: ChatListState,
    onEvent: (ChatListScreenEvent) -> Unit,
) {
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.DarkGray
                ),
                title = { Text(stringResource(R.string.chats_screen_title)) }
            )
        },
        bottomBar = { BottomNavBar(navController) }
    ) { paddingValues ->
        LoadingErrorPlaceHolder(error = state.error, isLoading = state.isLoading) {
            LazyColumn(
                contentPadding = paddingValues,
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                items(state.chats) {chat ->
                    ChatListItem(
                        chat = chat,
                        selfId = state.selfId
                    ) {
                        onEvent(ChatListScreenEvent.ChatClicked(navController, it))
                    }
                }
            }
        }
    }
}

fun NavGraphBuilder.chatListScreenComposable(navController: NavController) {
    composable(
        route = Screen.ChatListScreen.route
    ) {
        val viewModel: ChatListViewModel = hiltViewModel()
        ChatListScreen(navController, viewModel.state.value, viewModel::onEvent)
    }
}