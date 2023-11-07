package com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.recovery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.definitionsdefectsbuildingstructures.data.navigation.LogIn
import com.example.definitionsdefectsbuildingstructures.ui.components.authorization.recovery.BoxRecovery

@Composable
fun RecoveryScreen(
    navController: NavHostController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BoxRecovery(
            onRecoveryClick = { navController.navigate(LogIn.route) }
        )
    }
}