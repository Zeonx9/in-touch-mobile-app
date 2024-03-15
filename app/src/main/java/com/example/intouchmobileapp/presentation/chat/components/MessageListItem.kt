package com.example.intouchmobileapp.presentation.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intouchmobileapp.domain.model.Message
import com.example.intouchmobileapp.domain.model.User
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun MessageListItem(
    message: Message,
    fromOtherUser: Boolean,
    modifier: Modifier = Modifier
) {
    val formatter = DateTimeFormatter.ofPattern("HH:mm, dd.MM")
    val timeText = formatter.format(message.dateTime)
    val underText = if (fromOtherUser) "${message.author.username} $timeText" else timeText
    val background = if (fromOtherUser) Color.DarkGray else Color.Blue
    Row(
        horizontalArrangement = if (fromOtherUser) Arrangement.Start else Arrangement.End,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Column(
            horizontalAlignment = if (fromOtherUser) Alignment.Start else Alignment.End,
            modifier = Modifier.widthIn(min = 5.dp, max = 200.dp)
        ) {
            Card {
                Column(
                    modifier = Modifier
                        .background(background)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = message.text,
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = underText,
                color = Color.LightGray,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewMessageListItem() {
    val message = Message(
        author = User(
            id = 1,
            username = "user",
            dateOfBirth = LocalDate.of(2003, 1, 9),
            isOnline = false,
            patronymic = "Отчество",
            realName = "Имя",
            surname = "Фамилия",
            phoneNumber = "88005553535",
            profilePhotoId = null,
            thumbnailPhotoId = null
        ),
        chatId = 1,
        dateTime = LocalDateTime.now(),
        id = 1,
        isAuxiliary = false,
        text = "текст этого сообщения очень длинное, надеюсь оно на несколько строк разделится"
    )
    MessageListItem(message = message, fromOtherUser = true)
}