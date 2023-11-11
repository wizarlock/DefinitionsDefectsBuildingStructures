package com.example.definitionsdefectsbuildingstructures.ui.screens.projects.projectsList

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.definitionsdefectsbuildingstructures.data.navigation.AddProject
import com.example.definitionsdefectsbuildingstructures.ui.components.app.MyBotAppBar
import com.example.definitionsdefectsbuildingstructures.ui.components.app.MyTopAppBar
import com.example.definitionsdefectsbuildingstructures.ui.components.projects.listOfProjects.ListOfProjects


@Composable
fun ProjectsListScreen(navController: NavHostController) {
    val viewModel: ProjectsListViewModel = hiltViewModel()
    Scaffold(
        topBar = {
            MyTopAppBar(
                onSettingsClick = { /*TODO*/ },
                onAvatarClick = { /*TODO*/ }
            )
        },

        bottomBar = {
            MyBotAppBar(onAddClick = { navController.navigate(AddProject.route) })
        }
    )
    { paddingValues ->
        ListOfProjects(
            paddingValues = paddingValues,
            projects = viewModel.projects.collectAsState().value
        )
    }
}
