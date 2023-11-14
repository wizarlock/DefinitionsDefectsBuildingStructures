package com.example.definitionsdefectsbuildingstructures.ui.screens.workWithDrawing

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.definitionsdefectsbuildingstructures.R

@Composable
fun WorkWithDrawingScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ZoomableImageWithLayout()
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RoundButton("Аудио режим")
            RoundButton("Фото режим")
        }
    }
}

@Composable
fun ZoomableImageWithLayout() {
    val scale = remember { mutableStateOf(1f) }
    val offsetX = remember { mutableStateOf(0f) }
    val offsetY = remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .clip(RectangleShape)
            .fillMaxWidth()
            .fillMaxHeight(0.75f)
            .border(2.dp, Color.Black)
            .background(Color.White)
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale.value *= zoom
                    offsetX.value = (offsetX.value + pan.x).coerceIn(-500f, 500f)
                    offsetY.value = (offsetY.value + pan.y).coerceIn(-500f, 500f)
                }
            }
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer(
                    scaleX = maxOf(.5f, minOf(5f, scale.value)),
                    scaleY = maxOf(.5f, minOf(5f, scale.value)),
                    translationX = offsetX.value,
                    translationY = offsetY.value
                ),
            contentDescription = null,
            painter = painterResource(id = R.drawable.avatar_ex)
        )
    }
}


@Composable
private fun RoundButton(text: String) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
            .clickable { /* Add action on button click */ },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}