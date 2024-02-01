package com.example.intouchmobileapp.presentation.chat_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.example.intouchmobileapp.presentation.common.TextInCircle
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ChatTimeUnreadCount(
    lastInteractionTime: LocalDateTime?,
    unreadCount: Int
) {
    val formatter = DateTimeFormatter.ofPattern("HH:mm, dd.MM")
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = lastInteractionTime?.format(formatter) ?: "")
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