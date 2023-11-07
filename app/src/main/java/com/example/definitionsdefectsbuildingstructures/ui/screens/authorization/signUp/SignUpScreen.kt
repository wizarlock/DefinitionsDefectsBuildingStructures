package com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.signUp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.definitionsdefectsbuildingstructures.data.navigation.Start
import com.example.definitionsdefectsbuildingstructures.ui.components.authorization.signUp.BoxSignUp

@Composable
fun SignUpScreen(
    navController: NavHostController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BoxSignUp(
            onRegistrationClick = { navController.navigate(Start.route) }
        )
    }
}