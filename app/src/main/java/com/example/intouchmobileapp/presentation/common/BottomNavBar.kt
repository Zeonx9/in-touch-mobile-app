package com.example.intouchmobileapp.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.intouchmobileapp.presentation.Screen

@Composable
fun BottomNavBar(navController: NavController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        BottomNavItems.items.forEach { item ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    val icon = if (currentDestination?.hierarchy?.any { it.route == item.route } == true) {
                        item.selectedIcon
                    } else {
                        item.notSelectedIcon
                    }
                    Icon(
                        imageVector = icon,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(text = item.title)
                }
            )
        }
    }
}

object BottomNavItems {
    val items = listOf(
        BottomNavItem(
            title = "users",
            selectedIcon = Icons.Filled.AccountCircle,
            notSelectedIcon = Icons.Outlined.AccountCircle,
            route = Screen.UserListScreen.route
        ),
        BottomNavItem(
            title = "chats",
            selectedIcon = Icons.Default.Email,
            notSelectedIcon = Icons.Default.MailOutline,
            route = Screen.ChatListScreen.route
        ),
        BottomNavItem(
            title = "settings",
            selectedIcon = Icons.Filled.Settings,
            notSelectedIcon = Icons.Outlined.Settings,
            route = Screen.SettingsScreen.route
        )
    )
}

data class BottomNavItem(
    val title: String = "",
    val selectedIcon: ImageVector,
    val notSelectedIcon: ImageVector,
    val route: String
)