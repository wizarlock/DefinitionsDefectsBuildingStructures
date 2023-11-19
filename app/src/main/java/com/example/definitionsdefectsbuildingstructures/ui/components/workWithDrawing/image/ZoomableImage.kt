package com.example.definitionsdefectsbuildingstructures.ui.components.workWithDrawing.image

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.definitionsdefectsbuildingstructures.data.model.Label
import com.example.definitionsdefectsbuildingstructures.ui.components.updateLabel.writeBitmap
import com.example.definitionsdefectsbuildingstructures.ui.screens.workWithDrawing.actions.WorkWithDrawingAction
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File

import java.util.UUID

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ZoomableImage(
    fileName: String,
    isZoomEnabled: Boolean,
    realWidth: Int,
    realHeight: Int,
    uiAction: (WorkWithDrawingAction) -> Unit,
    listLabels: List<Label>,
    onLabelClick: (Label) -> Unit
) {
    val scale = remember { mutableStateOf(1f) }
    val offsetX = remember { mutableStateOf(0f) }
    val offsetY = remember { mutableStateOf(0f) }
    val imageX = remember { mutableStateOf(0f) }
    val imageY = remember { mutableStateOf(0f) }
    var currentPhotoPath = ""

    val context = LocalContext.current
    val hz = LocalDensity.current
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

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val bitmap = BitmapFactory.decodeFile(currentPhotoPath)
            val exif = ExifInterface(currentPhotoPath)
            val rotationAngle = when (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90
                ExifInterface.ORIENTATION_ROTATE_180 -> 180
                ExifInterface.ORIENTATION_ROTATE_270 -> 270
                else -> 0
            }
            val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, Matrix().apply { postRotate(rotationAngle.toFloat()) }, true)
            val fileLabel = UUID.randomUUID().toString() + ".jpg"
            val dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString()
            val file = File("$dir/$fileLabel")
            file.writeBitmap(rotatedBitmap, Bitmap.CompressFormat.JPEG, 100)
            uiAction(
                WorkWithDrawingAction.AddLabel(
                    imageX = imageX.value,
                    imageY = imageY.value,
                    fileName = fileLabel
                )
            )
        }
    }

    val modifierImage: Modifier = if (isZoomEnabled) {
        imageModifier.pointerInput(Unit) {
            if (permissionState.hasPermission) {
                detectTapGestures { offset ->
                    val offsetInDp = with(hz) {
                        Offset(offset.x / density, offset.y / density)
                    }
                    imageX.value = offsetInDp.x
                    imageY.value = offsetInDp.y
                    val strFileName = "photo"
                    val storageDir = context.cacheDir
                    val imgFile = File.createTempFile(strFileName, ".jpg", storageDir)
                    currentPhotoPath = imgFile.absolutePath
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    val imageUri = FileProvider.getUriForFile(
                        context,
                        "com.example.definitionsdefectsbuildingstructures.fileprovider",
                        imgFile
                    )
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                    cameraLauncher.launch(cameraIntent)

                }
            } else permissionState.launchPermissionRequest()
        }
    } else {
        imageModifier
    }

    Box(
        modifier = modifierBox
    ) {
        Box(
            modifier = imageModifier
                .align(Alignment.Center)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        LocalContext.current.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                            ?.resolve(fileName)
                    )
                    .size(Size.ORIGINAL)
                    .build(),
                modifier = Modifier
                    .align(Alignment.Center),
                contentDescription = null
            )
            listLabels.forEach { entry ->
                Canvas(
                    modifier = Modifier
                        .offset(
                            x = entry.x.dp,
                            y = entry.y.dp
                        )
                        .clickable { onLabelClick(entry) }
                ) {
                    drawCircle(
                        color = Color.Black,
                        radius = 1.dp.toPx(),
                        style = Stroke(1.dp.toPx())
                    )
                    drawCircle(
                        color = Color.Red,
                        radius = 0.75.dp.toPx(),
                        style = Fill

                    )
                }
            }
        }
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