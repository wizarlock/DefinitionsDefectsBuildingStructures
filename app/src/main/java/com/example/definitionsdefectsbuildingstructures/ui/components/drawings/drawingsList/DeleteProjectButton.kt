package com.example.definitionsdefectsbuildingstructures.ui.components.drawings.drawingsList

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DeleteProjectIcon(onDeleteClick: () -> Unit) {
    IconButton(
        onClick = { onDeleteClick() }
    ) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = "Delete",
            tint = Color.Red,
            modifier = Modifier
                .size(50.dp)
        )
    }
}