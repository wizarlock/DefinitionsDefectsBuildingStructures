package com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.start

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.definitionsdefectsbuildingstructures.data.navigation.LogIn
import com.example.definitionsdefectsbuildingstructures.data.navigation.SignUp
import com.example.definitionsdefectsbuildingstructures.ui.components.authorization.start.ActionButtons
import com.example.definitionsdefectsbuildingstructures.ui.components.authorization.start.LogoAndName

@Composable
fun StartScreen(
    navController: NavHostController
) {
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
            ){
        LogoAndName()
        ActionButtons(
            onLogInClick = { navController.navigate(LogIn.route) },
            onSignUpClick = { navController.navigate(SignUp.route) }
        )
    }
}