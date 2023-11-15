package com.example.definitionsdefectsbuildingstructures.ui.components.workWithDrawing

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.definitionsdefectsbuildingstructures.R

@Composable
fun AudioButton(
    onButtonClick: () -> Unit
) {
    var clickCount by remember { mutableStateOf(0) }
    Button(
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        onClick = {
            clickCount++
            onButtonClick()
        },
        shape = CircleShape,
        border = BorderStroke(2.dp, Color.Black),
        contentPadding = PaddingValues(25.dp),
        modifier = Modifier.padding(end = 10.dp)
    ) {
        Text(
            text = stringResource(id = R.string.audio_mode) + "\n" +
                    if (clickCount % 2 == 0) stringResource(R.string.audio_mode_off) else stringResource(R.string.audio_mode_on),
            fontSize = 20.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}