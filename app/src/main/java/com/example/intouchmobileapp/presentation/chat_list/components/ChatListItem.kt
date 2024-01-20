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
import com.example.intouchmobileapp.data.remote.dto.Chat

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
            if (chat.isPrivate) {
                val otherUser = chat.members.find { it.id != selfId }!!
                TextInCircle(
                    text = "${otherUser.realName[0]}${otherUser.surname[0]}",
                    background = Color.Gray,
                    color = Color.White,
                    size = 60,
                    textSize = 30
                )
                ChatInfo(
                    chatName = "${otherUser.realName} ${otherUser.surname}",
                    lastMessageText = chat.lastMessage?.text
                )
            } else {
                TextInCircle(
                    text = chat.group!!.name[0].uppercase(),
                    background = Color.Gray,
                    color = Color.White,
                    size = 60,
                    textSize = 30
                )
                ChatInfo(
                    chatName = chat.group.name,
                    lastMessageText = chat.lastMessage?.text
                )
            }
            ChatTimeUnreadCount(
                lastInteractionTime = chat.lastMessage?.dateTime,
                unreadCount = chat.unreadCounter
            )
        }
    }
}