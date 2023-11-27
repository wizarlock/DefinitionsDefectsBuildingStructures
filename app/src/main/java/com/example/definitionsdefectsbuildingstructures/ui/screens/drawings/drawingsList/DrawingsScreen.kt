package com.example.definitionsdefectsbuildingstructures.ui.screens.drawings.drawingsList

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.definitionsdefectsbuildingstructures.data.navigation.AddDrawing
import com.example.definitionsdefectsbuildingstructures.data.navigation.ProjectsList
import com.example.definitionsdefectsbuildingstructures.data.navigation.WorkWithDrawing
import com.example.definitionsdefectsbuildingstructures.ui.components.app.MyTopAppBar
import com.example.definitionsdefectsbuildingstructures.ui.components.drawings.drawingsList.AddDrawingIcon
import com.example.definitionsdefectsbuildingstructures.ui.components.drawings.drawingsList.BotDrawingBar
import com.example.definitionsdefectsbuildingstructures.ui.components.drawings.drawingsList.DeleteProjectIcon
import com.example.definitionsdefectsbuildingstructures.ui.components.drawings.drawingsList.DirectoryIcon
import com.example.definitionsdefectsbuildingstructures.ui.components.drawings.drawingsList.RecordContextButton
import com.example.definitionsdefectsbuildingstructures.ui.components.drawings.drawingsList.SelectDrawing
import com.example.definitionsdefectsbuildingstructures.ui.components.drawings.drawingsList.SettingsProjectButton
import com.example.definitionsdefectsbuildingstructures.ui.screens.drawings.drawingsList.actions.DrawingsAction

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DrawingsScreen(
    navController: NavHostController
) {
    val viewModel: DrawingViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    Scaffold(
        topBar = {
            MyTopAppBar(
                onSettingsClick = { /*TODO*/ },
                onAvatarClick = { /*TODO*/ }
            )
        },

        bottomBar = {
            BotDrawingBar()
        }
    )
    { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = uiState.projectName,
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )

            SelectDrawing(
                list = uiState.drawings.collectAsState().value,
                onDrawingClick = { drawing ->
                    viewModel.updateDrawing(drawingItem = drawing)
                    navController.navigate(WorkWithDrawing.route)
                },
                viewModel::onUiAction
            )
            AddDrawingIcon(onAddClick = { navController.navigate(AddDrawing.route) })
            SettingsProjectButton(onClick = {})
            RecordContextButton(viewModel::onUiAction, uiState)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DeleteProjectIcon(onDeleteClick = {
                    viewModel.onUiAction(DrawingsAction.DeleteProject)
                    navController.navigate(ProjectsList.route) { popUpTo(ProjectsList.route) }
                })
                DirectoryIcon (onDirectoryClick = {})
            }
        }
    }
}