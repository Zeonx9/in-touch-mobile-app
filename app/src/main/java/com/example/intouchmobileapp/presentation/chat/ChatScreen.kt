package com.example.intouchmobileapp.presentation.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.intouchmobileapp.presentation.chat.components.MessageListItem
import com.example.intouchmobileapp.presentation.chat_list.components.TextInCircle
import com.example.intouchmobileapp.presentation.common.LoadingErrorPlaceHolder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    navController: NavController,
    viewModel: ChatViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value
    val chat = viewModel.chat
    Scaffold (
        topBar = {
             TopAppBar(
                 colors = TopAppBarDefaults.topAppBarColors(
                     containerColor = Color.DarkGray
                 ),
                 title = {
                     Row (
                         verticalAlignment = Alignment.CenterVertically
                     ) {
                         TextInCircle(
                             text = chat.getAbbreviation(viewModel.selfId),
                             background = Color.Gray,
                             color = Color.White,
                             size = 60,
                             textSize = 30
                         )
                         Spacer(modifier = Modifier.width(10.dp))
                         Text(text = chat.getName(viewModel.selfId))
                     }
                 },
                 navigationIcon = {
                     IconButton(onClick = { navController.navigateUp() }) {
                         Icon(
                             imageVector = Icons.Default.ArrowBack,
                             contentDescription = "back Button"
                         )
                     }
                 }
             )
        },
        bottomBar = {
            Row (
                modifier = Modifier
                    .background(Color.DarkGray)
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp, 4.dp, 8.dp)
            ) {
                TextField(
                    value = state.messageText,
                    onValueChange = viewModel::updateMessageText,
                    label = {
                        Text(text = "type message...")
                    },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                IconButton(
                    onClick = viewModel::sendMessage
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "send button"
                    )
                }
            }
        }
    ) {
        LoadingErrorPlaceHolder(error = state.error, isLoading = state.isLoading) {
            val columnState = rememberLazyListState()
            LaunchedEffect(state.messages){
                if (state.messages.isNotEmpty()) {
                    columnState.scrollToItem(state.messages.size - 1)
                }
            }
            LazyColumn (
                state = columnState,
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 10.dp)
            ) {
                items(state.messages) { message ->
                    MessageListItem(
                        message = message,
                        fromOtherUser = viewModel.selfId != message.author.id
                    )
                }
            }
        }
    }
}