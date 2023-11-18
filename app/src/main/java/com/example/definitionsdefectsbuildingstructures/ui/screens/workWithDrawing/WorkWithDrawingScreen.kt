package com.example.definitionsdefectsbuildingstructures.ui.screens.workWithDrawing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.definitionsdefectsbuildingstructures.ui.components.workWithDrawing.RoundButtons
import com.example.definitionsdefectsbuildingstructures.ui.components.workWithDrawing.image.ZoomableImage

@Composable
fun WorkWithDrawingScreen(navController: NavHostController) {
    var photoOn by remember { mutableStateOf(false) }
    val viewModel: WorkWithDrawingViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ZoomableImage(
            fileName = uiState.fileName,
            isZoomEnabled = photoOn,
            realHeight = uiState.height,
            realWidth = uiState.width,
            uiAction = viewModel::onUiAction,
            listLabels = uiState.labels.collectAsState().value
        )
        Spacer(modifier = Modifier.height(16.dp))
        RoundButtons(
            viewModel::onUiAction,
            onPhotoClick = { bool ->
                photoOn = bool
            }
        )
    }
}