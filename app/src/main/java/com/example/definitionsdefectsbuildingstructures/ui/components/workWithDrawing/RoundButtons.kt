package com.example.definitionsdefectsbuildingstructures.ui.components.workWithDrawing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun RoundButtons(
    onAudioClick: () -> Unit,
    onPhotoClick: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        AudioButton(
            onButtonClick = { onAudioClick() },

            )
        PhotoButton(
            onButtonClick = { bool ->
                onPhotoClick(bool)
            }
        )
    }
}