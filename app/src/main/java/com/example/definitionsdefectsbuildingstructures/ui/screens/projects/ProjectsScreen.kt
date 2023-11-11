package com.example.definitionsdefectsbuildingstructures.ui.screens.projects


import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.definitionsdefectsbuildingstructures.ui.components.app.MyBotAppBar
import com.example.definitionsdefectsbuildingstructures.ui.components.app.MyTopAppBar
import com.example.definitionsdefectsbuildingstructures.ui.components.projects.ListOfProjects


@Composable
fun ProjectsScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            MyTopAppBar(
                onSettingsClick = { /*TODO*/ },
                onAvatarClick = { /*TODO*/ }
            )
        },

        bottomBar = {
            MyBotAppBar(onAddClick = { /*TODO*/ })
        }
    )
    { paddingValues ->
        ListOfProjects(paddingValues = paddingValues, )
    }
}