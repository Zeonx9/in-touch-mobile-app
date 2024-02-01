package com.example.intouchmobileapp.presentation.common

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

fun NavController.navigateAndReplaceStartDestination(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            inclusive = true
        }
    }
    graph.setStartDestination(route)
}