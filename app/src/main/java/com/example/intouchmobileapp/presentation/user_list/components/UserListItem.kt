package com.example.intouchmobileapp.presentation.user_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.intouchmobileapp.domain.model.User
import com.example.intouchmobileapp.presentation.common.TextInCircle

@Composable
fun UserListItem(user: User, onClick: (User) -> Unit) {
    Box(
        modifier = Modifier.clickable { onClick(user) }
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            TextInCircle(
                text = user.abbreviation(),
                background = Color.Gray,
                color = Color.White,
                size = 60,
                textSize = 30
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = user.fullName())
        }
    }

}