package com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.recovery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.definitionsdefectsbuildingstructures.data.navigation.LogIn
import com.example.definitionsdefectsbuildingstructures.ui.components.authorization.recovery.BoxRecovery

@Composable
fun RecoveryScreen(
    navController: NavHostController
) {
    val viewModel: RecoveryViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BoxRecovery(
            uiState,
            viewModel::onUiAction,
            onRecoveryClick = { navController.navigate(LogIn.route) { popUpTo(LogIn.route) } }
        )
    }
}