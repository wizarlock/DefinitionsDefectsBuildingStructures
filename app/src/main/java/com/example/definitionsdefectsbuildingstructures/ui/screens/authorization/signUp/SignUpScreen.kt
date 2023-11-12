package com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.signUp

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
import com.example.definitionsdefectsbuildingstructures.data.navigation.Start
import com.example.definitionsdefectsbuildingstructures.ui.components.authorization.signUp.BoxSignUp

@Composable
fun SignUpScreen(
    navController: NavHostController
) {
    val viewModel: SignUpViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val uiStateBoolean by viewModel.uiStateBoolean.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BoxSignUp(
            uiState,
            uiStateBoolean,
            viewModel::onUiAction,
            onRegistrationClick = {
                if (viewModel.areFieldsValid()) navController.navigate(Start.route) {
                    popUpTo(
                        Start.route
                    )
                }
            }
        )
    }
}