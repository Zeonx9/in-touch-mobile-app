package com.example.intouchmobileapp.presentation.chat_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.intouchmobileapp.domain.model.Chat
import com.example.intouchmobileapp.presentation.common.TextInCircle

@Composable
fun ChatListItem(
    chat: Chat,
    selfId: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box (
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            TextInCircle(
                text = chat.getAbbreviation(selfId),
                background = Color.Gray,
                color = Color.White,
                size = 60,
                textSize = 30
            )
            ChatInfo(
                chatName = chat.getName(selfId),
                lastMessageText = chat.lastMessage?.text
            )
            ChatTimeUnreadCount(
                lastInteractionTime = chat.lastMessage?.dateTime,
                unreadCount = chat.unreadCounter
            )
        }
    }
}