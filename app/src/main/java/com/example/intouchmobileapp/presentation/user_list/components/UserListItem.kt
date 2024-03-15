package com.example.intouchmobileapp.presentation.user_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.intouchmobileapp.domain.model.User
import com.example.intouchmobileapp.presentation.common.ThumbnailMedium
import java.time.LocalDate

@Composable
fun UserListItem(user: User, isLoading: Boolean = false, onClick: (User) -> Unit) {
    Box(
        modifier = Modifier.clickable { onClick(user) }
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            ThumbnailMedium(
                url = user.thumbnailUrl,
                text = user.abbreviation,
                background = Color.Gray,
                color = Color.White,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = user.fullName)
            Spacer(modifier = Modifier.weight(1f))
            if (isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview
@Composable
fun UserListItemPreview() {
    UserListItem(
        user = User(
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
    ) {

    }
}