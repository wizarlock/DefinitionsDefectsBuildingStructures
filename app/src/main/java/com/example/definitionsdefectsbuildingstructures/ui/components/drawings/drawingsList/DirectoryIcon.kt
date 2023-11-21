package com.example.definitionsdefectsbuildingstructures.ui.components.drawings.drawingsList

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.definitionsdefectsbuildingstructures.R

@Composable
fun DirectoryIcon(onDirectoryClick: () -> Unit) {
    IconButton(
        onClick = { onDirectoryClick() }
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_folder_open_black_36dp),
            contentDescription = "",
            modifier = Modifier
                .size(50.dp)
        )
    }
}