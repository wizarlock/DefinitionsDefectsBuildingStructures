package com.example.definitionsdefectsbuildingstructures.ui.components.workWithDrawing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.definitionsdefectsbuildingstructures.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawingTopBar(onReloadClick: () -> Unit) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(
                onClick = { onReloadClick() }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_settings_overscan_black_24dp),
                    contentDescription = "reload",
                    modifier = Modifier
                        .size(32.dp)
                )
            }
        },
    )
}
