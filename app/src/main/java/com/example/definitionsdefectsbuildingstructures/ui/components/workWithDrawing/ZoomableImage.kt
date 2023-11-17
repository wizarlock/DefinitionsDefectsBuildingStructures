package com.example.definitionsdefectsbuildingstructures.ui.components.workWithDrawing

import android.os.Environment
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun ZoomableImage(
    fileName: String,
    isZoomEnabled: Boolean
) {
    val scale = remember { mutableStateOf(1f) }
    val offsetX = remember { mutableStateOf(0f) }
    val offsetY = remember { mutableStateOf(0f) }
    val clickCoordinates = remember { mutableStateOf(Pair(0f, 0f)) }

    val baseModifier = Modifier
        .clip(RectangleShape)
        .fillMaxWidth()
        .fillMaxHeight(0.75f)
        .border(2.dp, Color.Black)
        .background(Color.White)

    val modifier: Modifier = if (!isZoomEnabled) {
        baseModifier.pointerInput(Unit) {
            detectTransformGestures { _, pan, zoom, _ ->
                scale.value *= zoom
                offsetX.value = (offsetX.value + pan.x).coerceIn(-1000f, 1000f)
                offsetY.value = (offsetY.value + pan.y).coerceIn(-1000f, 1000f)
            }
        }
    } else {
        baseModifier
    }

    Box(
        modifier = modifier
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(
                    LocalContext.current.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                        ?.resolve("$fileName.png")
                )
                .size(Size.ORIGINAL)
                .build(),
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer(
                    scaleX = maxOf(1f, minOf(10f, scale.value)),
                    scaleY = maxOf(1f, minOf(10f, scale.value)),
                    translationX = offsetX.value,
                    translationY = offsetY.value
                )
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        val imageX = ((offset.x - offsetX.value) / scale.value)
                        val imageY = ((offset.y - offsetY.value) / scale.value)
                        clickCoordinates.value = Pair(imageX, imageY)
                        Log.e("HYYYYYYYYYYYYYYYYYYYYYYYI", "Clicked at: $imageX, $imageY")
                    }
                },
            contentDescription = null,
        )
    }
}