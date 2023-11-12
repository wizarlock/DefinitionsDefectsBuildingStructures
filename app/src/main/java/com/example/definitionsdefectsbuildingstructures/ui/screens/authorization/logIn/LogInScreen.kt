package com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.logIn

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
import com.example.definitionsdefectsbuildingstructures.data.navigation.ProjectsList
import com.example.definitionsdefectsbuildingstructures.data.navigation.Recovery
import com.example.definitionsdefectsbuildingstructures.data.navigation.navigateSingleTopToAndRetainState
import com.example.definitionsdefectsbuildingstructures.ui.components.authorization.logIn.BoxLogIn
import com.example.definitionsdefectsbuildingstructures.ui.components.authorization.logIn.LinkToRecovery

@Composable
fun LogInScreen(
    navController: NavHostController
) {
    val viewModel: LogInViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val uiStateBoolean by viewModel.uiStateBoolean.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BoxLogIn(
            uiState,
            uiStateBoolean,
            viewModel::onUiAction,
            onEnteringClick = {
                if (viewModel.areFieldsValid()) {
                    navController.popBackStack("start", true)
                    navController.navigateSingleTopToAndRetainState(ProjectsList.route)
                }
            }
        )
        LinkToRecovery(onLinkClick = { navController.navigateSingleTopToAndRetainState(Recovery.route) })
    }
}