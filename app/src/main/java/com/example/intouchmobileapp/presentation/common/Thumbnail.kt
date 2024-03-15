package com.example.intouchmobileapp.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent

@Composable
fun Thumbnail(
    url: String?,
    size: Int,
    text: String,
    textSize: Int,
    background: Color,
    color: Color,
) {
    SubcomposeAsyncImage(
        model = url,
        contentDescription = "thumbnail downloaded from $url",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(size.dp)
            .clip(CircleShape)
    ) {
        when(painter.state) {
            is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
            is AsyncImagePainter.State.Loading -> {
                Box {
                    CircularProgressIndicator()
                }
            }
            else -> {
                TextInCircle(text, background, color, size, textSize)
            }
        }
    }
}

@Composable
fun ThumbnailSmall(
    url: String?,
    text: String,
    background: Color,
    color: Color,
) {
    Thumbnail(url = url, size = 40, text = text, textSize = 20, background = background, color = color)
}

@Composable
fun ThumbnailMedium(
    url: String?,
    text: String,
    background: Color,
    color: Color,
) {
    Thumbnail(url = url, size = 60, text = text, textSize = 30, background = background, color = color)
}