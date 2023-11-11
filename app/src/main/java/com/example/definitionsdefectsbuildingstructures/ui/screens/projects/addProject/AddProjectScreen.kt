package com.example.definitionsdefectsbuildingstructures.ui.screens.projects.addProject

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
import com.example.definitionsdefectsbuildingstructures.ui.components.projects.addProject.BoxAddProject

@Composable
fun AddProjectScreen(navController: NavHostController) {
    val viewModel: AddProjectViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val uiStateBoolean by viewModel.uiStateBoolean.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BoxAddProject(
            uiState,
            uiStateBoolean,
            viewModel::onUiAction,
            onSavingClick = {
                if (viewModel.areFieldsValid()) navController.navigate(ProjectsList.route) { popUpTo(ProjectsList.route) }
            }
        )
    }
}
