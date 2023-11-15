package com.example.definitionsdefectsbuildingstructures.ui.screens.drawings.addDrawing

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
import com.example.definitionsdefectsbuildingstructures.data.navigation.Drawings
import com.example.definitionsdefectsbuildingstructures.ui.components.drawings.addDrawing.BoxAddDrawing
import com.example.definitionsdefectsbuildingstructures.ui.screens.drawings.addDrawing.actions.AddDrawingAction

@Composable
fun AddDrawingScreen(navController: NavHostController) {
    val viewModel: AddDrawingViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val uiStateBoolean by viewModel.uiStateBoolean.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BoxAddDrawing(
            uiState,
            uiStateBoolean,
            viewModel::onUiAction,
            onSavingClick = {
                if (viewModel.areFieldsValid()) {
                    viewModel.onUiAction(AddDrawingAction.SaveDrawing)
                    viewModel.test()
                    navController.navigate(Drawings.route) { popUpTo(Drawings.route) }
                }
            }
        )
    }
}
