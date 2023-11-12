package com.example.definitionsdefectsbuildingstructures.data.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

fun NavHostController.navigateSingleTopToAndRetainState(route: String) {
    this.navigate(route) {
        popUpTo(this@navigateSingleTopToAndRetainState.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}