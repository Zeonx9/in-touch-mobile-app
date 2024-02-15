package com.example.intouchmobileapp.presentation.user_list

import com.example.intouchmobileapp.domain.model.User

data class UserListState(
    val users: List<User> = emptyList(),
    val error: String ="",
    val isLoading: Boolean = false,
    val loadingUserId: Int = -1
)
