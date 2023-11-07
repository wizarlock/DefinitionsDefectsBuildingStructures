package com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.logIn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.definitionsdefectsbuildingstructures.data.navigation.Projects
import com.example.definitionsdefectsbuildingstructures.data.navigation.Recovery
import com.example.definitionsdefectsbuildingstructures.ui.components.authorization.logIn.BoxLogIn
import com.example.definitionsdefectsbuildingstructures.ui.components.authorization.logIn.LinkToRecovery


@Composable
fun LogInScreen(
    navController: NavHostController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BoxLogIn(
            onEnteringClick = { navController.navigate(Projects.route) }
        )
        LinkToRecovery(onLinkClick = { navController.navigate(Recovery.route) })
    }
}