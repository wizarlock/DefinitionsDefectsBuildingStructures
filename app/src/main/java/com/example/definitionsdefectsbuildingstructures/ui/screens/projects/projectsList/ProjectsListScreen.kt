package com.example.definitionsdefectsbuildingstructures.ui.screens.projects.projectsList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.definitionsdefectsbuildingstructures.R
import com.example.definitionsdefectsbuildingstructures.data.navigation.AddProject
import com.example.definitionsdefectsbuildingstructures.data.navigation.Drawings
import com.example.definitionsdefectsbuildingstructures.ui.components.projects.listOfProjects.MyBotAppBar
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.my_projects),
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
            ListOfProjects(
                paddingValues = paddingValues,
                projects = viewModel.projects.collectAsState().value,
                onCardClick = { project ->
                    viewModel.updateProject(projectItem = project)
                    navController.navigate(Drawings.route)
                }
            )
        }
    }
}
