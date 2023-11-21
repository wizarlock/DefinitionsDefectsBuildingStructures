package com.example.definitionsdefectsbuildingstructures.ui.components.drawings.drawingsList

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AddDrawingIcon(onAddClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onAddClick() },
        modifier = Modifier
            .padding(5.dp),
        shape = CircleShape,
        containerColor = Color.White,
        contentColor = Color.Black
    ) {
        Icon(Icons.Rounded.Add, contentDescription = null)
    }
}