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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.intouchmobileapp.presentation.chat_list.components.ChatListItem
import com.example.intouchmobileapp.presentation.common.BottomNavBar
import com.example.intouchmobileapp.presentation.common.LoadingErrorPlaceHolder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    navController: NavController,
    viewModel: ChatListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.DarkGray
                ),
                title = {
                    Text(text = "Chats")
                }
            )
        },
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) {
        LoadingErrorPlaceHolder(error = state.error, isLoading = state.isLoading) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier
                    .padding(it)
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                items(state.chats) {chat ->
                    ChatListItem(
                        chat = chat,
                        selfId = viewModel.selfId
                    ) {
                        viewModel.openChat(navController, chat.id)
                    }
                }
            }
        }
    }
}