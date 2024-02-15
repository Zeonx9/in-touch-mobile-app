package com.example.intouchmobileapp.presentation.user_list

import androidx.navigation.NavController
import com.example.intouchmobileapp.domain.model.User

interface UserListScreenEvent {
    data class UserClickEvent(val navController: NavController, val user: User) : UserListScreenEvent
}