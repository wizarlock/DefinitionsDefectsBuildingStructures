package com.example.definitionsdefectsbuildingstructures.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import com.example.definitionsdefectsbuildingstructures.data.navigation.AddDrawing
import com.example.definitionsdefectsbuildingstructures.data.navigation.AddProject
import com.example.definitionsdefectsbuildingstructures.data.navigation.Drawings
import com.example.definitionsdefectsbuildingstructures.data.navigation.LogIn
import com.example.definitionsdefectsbuildingstructures.data.navigation.ProjectsList
import com.example.definitionsdefectsbuildingstructures.data.navigation.Recovery
import com.example.definitionsdefectsbuildingstructures.data.navigation.SignUp
import com.example.definitionsdefectsbuildingstructures.data.navigation.Start
import com.example.definitionsdefectsbuildingstructures.data.navigation.UpdateLabel
import com.example.definitionsdefectsbuildingstructures.data.navigation.WorkWithDrawing
import com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.logIn.LogInScreen
import com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.recovery.RecoveryScreen
import com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.signUp.SignUpScreen
import com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.start.StartScreen
import com.example.definitionsdefectsbuildingstructures.ui.screens.drawings.addDrawing.AddDrawingScreen
import com.example.definitionsdefectsbuildingstructures.ui.screens.drawings.drawingsList.DrawingsScreen
import com.example.definitionsdefectsbuildingstructures.ui.screens.projects.addProject.AddProjectScreen
import com.example.definitionsdefectsbuildingstructures.ui.screens.projects.projectsList.ProjectsListScreen
import com.example.definitionsdefectsbuildingstructures.ui.screens.updateLabel.UpdateLabelScreen
import com.example.definitionsdefectsbuildingstructures.ui.screens.workWithDrawing.WorkWithDrawingScreen

@Composable
fun AppNavHost(
    modifier: Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Start.route
    ) {
        composable(Start.route) {
            StartScreen(navController = navController)
        }
        composable(LogIn.route) {
            LogInScreen(navController = navController)
        }

        composable(Recovery.route) {
            RecoveryScreen(navController = navController)
        }

        composable(SignUp.route) {
            SignUpScreen(navController = navController)
        }

        composable(ProjectsList.route) {
            ProjectsListScreen(navController = navController)
        }

        composable(AddProject.route) {
            AddProjectScreen(navController = navController)
        }

        composable(Drawings.route) {
            DrawingsScreen(navController = navController)
        }

        composable(AddDrawing.route) {
            AddDrawingScreen(navController = navController)
        }

        composable(WorkWithDrawing.route) {
            WorkWithDrawingScreen(navController = navController)
        }

        composable(UpdateLabel.route) {
            UpdateLabelScreen(navController = navController)
        }
    }
}