package com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.start

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.definitionsdefectsbuildingstructures.data.navigation.ProjectsList
import com.example.definitionsdefectsbuildingstructures.data.navigation.navigateSingleTopToAndRetainState
import com.example.definitionsdefectsbuildingstructures.ui.components.authorization.start.ActionButtons
import com.example.definitionsdefectsbuildingstructures.ui.components.authorization.start.LogoAndName

@Composable
fun StartScreen(
    navController: NavHostController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoAndName()
        ActionButtons(
            onLogInClick = {
                navController.navigateSingleTopToAndRetainState(ProjectsList.route)
            }
        )
    }
}