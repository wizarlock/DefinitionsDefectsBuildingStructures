package com.example.definitionsdefectsbuildingstructures.ui.screens.drawings.drawingsList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.definitionsdefectsbuildingstructures.data.navigation.AddDrawing
import com.example.definitionsdefectsbuildingstructures.ui.components.app.MyBotAppBar
import com.example.definitionsdefectsbuildingstructures.ui.components.app.MyTopAppBar
import com.example.definitionsdefectsbuildingstructures.ui.components.drawings.drawingsList.RecordContextButton
import com.example.definitionsdefectsbuildingstructures.ui.components.drawings.drawingsList.SelectDrawing
import com.example.definitionsdefectsbuildingstructures.ui.components.drawings.drawingsList.SettingsProjectButton

@Composable
fun DrawingsScreen(
    navController: NavHostController
) {
    val viewModel: DrawingViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            MyTopAppBar(
                onSettingsClick = { /*TODO*/ },
                onAvatarClick = { /*TODO*/ }
            )
        },

        bottomBar = {
            MyBotAppBar(onAddClick = { navController.navigate(AddDrawing.route) })
        }
    )
    { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = uiState.projectName,
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )

            SelectDrawing(
                list = uiState.drawings.collectAsState().value,
                paddingValues = paddingValues
            )
            SettingsProjectButton(onClick = {})
            RecordContextButton(onClick = {})
        }
    }
}