package com.example.definitionsdefectsbuildingstructures

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.definitionsdefectsbuildingstructures.ui.navigation.AppNavHost
import com.example.definitionsdefectsbuildingstructures.ui.theme.DefinitionsDefectsBuildingStructuresTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            DefinitionsDefectsBuildingStructuresTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavHost(
                        modifier = Modifier,
                        navController = navController
                    )
                }
            }
        }
    }
}

