package com.example.intouchmobileapp.presentation.chat_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextInCircle(
    text: String,
    background: Color,
    color: Color,
    size: Int,
    textSize: Int
) {
    Box(
        modifier = Modifier
            .size(size.dp) // 60
            .clip(shape = CircleShape)
            .background(color = background)
    ) {
        Text(
            text = text,
            fontSize = textSize.sp, // 30
            color = color,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}