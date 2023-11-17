package com.example.definitionsdefectsbuildingstructures.ui.components.workWithDrawing

import android.Manifest
import android.os.Environment
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
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ZoomableImage(
    fileName: String,
    isZoomEnabled: Boolean,
    realWidth: Int,
    realHeight: Int
) {
    val scale = remember { mutableStateOf(1f) }
    val offsetX = remember { mutableStateOf(0f) }
    val offsetY = remember { mutableStateOf(0f) }

    val permissionState = rememberPermissionState(Manifest.permission.CAMERA)

    val boxModifier = Modifier
        .clip(RectangleShape)
        .fillMaxWidth()
        .fillMaxHeight(0.75f)
        .border(2.dp, Color.Black)
        .background(Color.White)

    val modifierBox: Modifier = if (!isZoomEnabled) {
        boxModifier.pointerInput(Unit) {
            detectTransformGestures { _, pan, zoom, _ ->
                scale.value *= zoom
                offsetX.value = (offsetX.value + pan.x).coerceIn(-1000f, 1000f)
                offsetY.value = (offsetY.value + pan.y).coerceIn(-1000f, 1000f)
            }
        }
    } else {
        boxModifier
    }

    val imageModifier = Modifier
        .graphicsLayer(
            scaleX = maxOf(1f, minOf(10f, scale.value)),
            scaleY = maxOf(1f, minOf(10f, scale.value)),
            translationX = offsetX.value,
            translationY = offsetY.value
        )

    val modifierImage: Modifier = if (isZoomEnabled) {
        imageModifier.pointerInput(Unit) {
            if (permissionState.hasPermission) {
                detectTapGestures { offset ->
                    val coefficientWidth = realWidth.toDouble() / size.width.toDouble()
                    val coefficientHeight = realHeight.toDouble() / size.height.toDouble()
                    val imageX = offset.x * coefficientWidth
                    val imageY = offset.y * coefficientHeight
                }
            } else permissionState.launchPermissionRequest()
        }
    } else {
        imageModifier
    }

    Box(
        modifier = modifierBox
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(
                    LocalContext.current.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                        ?.resolve(fileName)
                )
                .size(Size.ORIGINAL)
                .build(),
            modifier = modifierImage
                .align(Alignment.Center),
            contentDescription = null,
        )
    }
}