package com.example.intouchmobileapp.presentation.chat_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.ChatInfo(
    chatName: String,
    lastMessageText: String
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .padding(8.dp)
    ) {
        Text(
            text = chatName,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = lastMessageText,
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis
        )
    }
}