package com.example.intouchmobileapp.presentation.chat_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

@Composable
fun ChatTimeUnreadCount(
    lastInteractionTime: String,
    unreadCount: Int
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = lastInteractionTime.slice(IntRange(11, 15)))
        if (unreadCount != 0) {
            TextInCircle(
                text = "$unreadCount",
                background = Color.Blue,
                color = Color.White,
                size = 24,
                textSize = 12
            )
        }
    }
}