package com.example.definitionsdefectsbuildingstructures.ui.components.workWithDrawing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.definitionsdefectsbuildingstructures.ui.screens.workWithDrawing.WorkWithDrawingUiState
import com.example.definitionsdefectsbuildingstructures.ui.screens.workWithDrawing.actions.WorkWithDrawingAction

@Composable
fun RoundButtons(
    uiAction: (WorkWithDrawingAction) -> Unit,
    onPhotoClick: (Boolean) -> Unit,
    uiState: WorkWithDrawingUiState
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        AudioButton(uiAction, uiState)
        PhotoButton(
            onButtonClick = { bool ->
                onPhotoClick(bool)
            }
        )
    }
}