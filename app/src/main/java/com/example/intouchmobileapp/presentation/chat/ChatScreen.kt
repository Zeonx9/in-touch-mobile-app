package com.example.intouchmobileapp.presentation.chat

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.intouchmobileapp.R
import com.example.intouchmobileapp.common.Constants
import com.example.intouchmobileapp.presentation.Screen
import com.example.intouchmobileapp.presentation.chat.components.MessageListItem
import com.example.intouchmobileapp.presentation.common.LoadingErrorPlaceHolder
import com.example.intouchmobileapp.presentation.common.ThumbnailSmall

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    navController: NavController,
    state: ChatState,
    onEvent: (ChatScreenEvent) -> Unit,
) {
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
                         ThumbnailSmall(
                             url = state.chat!!.getThumbnailUrl(state.selfId),
                             text = state.chat.getAbbreviation(state.selfId),
                             background = Color.Gray,
                             color = Color.White
                         )
                         Spacer(modifier = Modifier.width(10.dp))
                         Text(text = state.chat.getName(state.selfId))
                     }
                 },
                 navigationIcon = {
                     IconButton(onClick = { onEvent(ChatScreenEvent.UpEvent(navController)) }) {
                         Icon(
                             imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                             contentDescription = stringResource(R.string.up_button_description)
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
                    onValueChange = { onEvent(ChatScreenEvent.MessageTextChanged(it)) },
                    placeholder = {
                        Text(text = stringResource(R.string.message_text_field_placeholder_text))
                    },
                    modifier = Modifier
                        .weight(1f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                IconButton(
                    onClick = { onEvent(ChatScreenEvent.SendClicked) }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = stringResource(R.string.send_icon_button_description)
                    )
                }
            }
        }
    ) { paddingValues ->
        LoadingErrorPlaceHolder(error = state.error, isLoading = state.isLoading) {
            val columnState = rememberLazyListState()
            LaunchedEffect(state.messages){
                if (state.messages.isNotEmpty()) {
                    columnState.scrollToItem(state.messages.size - 1)
                }
            }
            LazyColumn (
                contentPadding = paddingValues,
                state = columnState,
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                items(state.messages) { message ->
                    MessageListItem(
                        message = message,
                        fromOtherUser = state.selfId != message.author.id
                    )
                }
            }
        }
    }
}

fun NavGraphBuilder.chatScreenComposable(navController: NavController) {
    composable(
        route = Screen.ChatScreen.route + "/{${Constants.PARAM_CHAT_ID}}",
        arguments = listOf(
            navArgument(Constants.PARAM_CHAT_ID) {
                type = NavType.IntType
            }
        ),
        enterTransition = {
            slideIntoContainer(
                animationSpec = tween(300, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        },
        exitTransition = {
            slideOutOfContainer(
                animationSpec = tween(300, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
        }
    ) {
        val viewModel: ChatViewModel = hiltViewModel()
        ChatScreen(navController, viewModel.state.value, viewModel::onEvent)
    }
}