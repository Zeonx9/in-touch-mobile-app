package com.example.intouchmobileapp.presentation.chat_list

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.intouchmobileapp.presentation.Screen
import com.example.intouchmobileapp.presentation.chat_list.components.ChatListItem

@Composable
fun ChatListScreen(
    navController: NavController,
    viewModel: ChatListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(state.chats) {chat ->
                ChatListItem(
                    chat = chat,
                    selfId = viewModel.selfId
                ) {
                    Log.d(javaClass.name, "clicked on item chat_id=${chat.id}")
                    navController.navigate(route = Screen.ChatScreen.withArgs(chat.id))
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .width(64.dp)
            )
        }
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = Color.Red
            )
        }
    }
}