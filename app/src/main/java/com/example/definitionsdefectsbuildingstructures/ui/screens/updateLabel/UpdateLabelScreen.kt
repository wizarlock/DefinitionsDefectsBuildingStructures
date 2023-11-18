package com.example.definitionsdefectsbuildingstructures.ui.screens.updateLabel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.definitionsdefectsbuildingstructures.data.navigation.WorkWithDrawing
import com.example.definitionsdefectsbuildingstructures.ui.components.updateLabel.LabelImage
import com.example.definitionsdefectsbuildingstructures.ui.components.updateLabel.RowOfActions
import com.example.definitionsdefectsbuildingstructures.ui.components.updateLabel.SaveLabelButton
import com.example.definitionsdefectsbuildingstructures.ui.screens.updateLabel.actions.UpdateLabelAction

@Composable
fun UpdateLabelScreen(navController: NavHostController) {
    val viewModel: UpdateLabelViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LabelImage(fileName = uiState.fileName)
        RowOfActions(
            onDeleteClick = {
                viewModel.onUiAction(UpdateLabelAction.DeleteLabel)
                navController.navigate(WorkWithDrawing.route) { popUpTo(WorkWithDrawing.route) }
            },
            uiAction = viewModel::onUiAction
        )
        SaveLabelButton(
            onSaveClick = {
                viewModel.onUiAction(UpdateLabelAction.UpdateLabel(uiState.fileName))
                navController.navigate(WorkWithDrawing.route) { popUpTo(WorkWithDrawing.route) }
            }
        )
    }
}