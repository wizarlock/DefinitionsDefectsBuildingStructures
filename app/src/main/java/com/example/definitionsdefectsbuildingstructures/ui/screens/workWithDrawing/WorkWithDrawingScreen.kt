package com.example.definitionsdefectsbuildingstructures.ui.screens.workWithDrawing

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.definitionsdefectsbuildingstructures.data.datastore.UserPreferences
import com.example.definitionsdefectsbuildingstructures.data.navigation.UpdateLabel
import com.example.definitionsdefectsbuildingstructures.ui.components.workWithDrawing.DrawingTopBar
import com.example.definitionsdefectsbuildingstructures.ui.components.workWithDrawing.RoundButtons
import com.example.definitionsdefectsbuildingstructures.ui.components.workWithDrawing.image.ZoomableImage
import com.example.definitionsdefectsbuildingstructures.ui.screens.workWithDrawing.actions.WorkWithDrawingAction

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WorkWithDrawingScreen(navController: NavHostController) {
    var photoOn by remember { mutableStateOf(false) }
    val viewModel: WorkWithDrawingViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            DrawingTopBar(
                onReloadClick = {
                    uiState.initialOffsetX.value = 0f
                    uiState.initialScale.value = 1f
                    uiState.initialOffsetY.value = 0f
                },
                onExportClick = {
                    viewModel.onUiAction(WorkWithDrawingAction.SaveProgress)
                }
            )
        },
    )
    { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ZoomableImage(
                uiState = uiState,
                isZoomEnabled = photoOn,
                uiAction = viewModel::onUiAction,
                onLabelClick = { label ->
                    viewModel.updateLabel(label)
                    navController.navigate(UpdateLabel.route)
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
            RoundButtons(
                viewModel::onUiAction,
                onPhotoClick = { bool ->
                    photoOn = bool
                },
                uiState = uiState
            )
        }
    }

}