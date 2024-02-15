package com.example.intouchmobileapp.presentation.user_list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.intouchmobileapp.R
import com.example.intouchmobileapp.presentation.Screen
import com.example.intouchmobileapp.presentation.common.BottomNavBar
import com.example.intouchmobileapp.presentation.common.LoadingErrorPlaceHolder
import com.example.intouchmobileapp.presentation.user_list.components.UserListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    navController: NavController,
    state: UserListState,
    onEvent: (UserListScreenEvent) -> Unit
) {
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(R.string.user_list_screen_title)) }
            )
        },
        bottomBar = { BottomNavBar(navController) }
    ) { paddingValues ->
        LoadingErrorPlaceHolder(error = state.error, isLoading = state.isLoading) {
            LazyColumn(
                contentPadding = paddingValues
            ) {
                items(state.users) { user ->
                    UserListItem(user = user, isLoading = state.loadingUserId == user.id) {
                        onEvent(UserListScreenEvent.UserClickEvent(navController, user))
                    }
                }
            }
        }
    }
}

fun NavGraphBuilder.userListComposable(navController: NavController) {
    composable(
        route = Screen.UserListScreen.route
    ) {
        val viewModel: UserListViewModel = hiltViewModel()
        UserListScreen(navController = navController, viewModel.state.value, viewModel::onEvent)
    }
}