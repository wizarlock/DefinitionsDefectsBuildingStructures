package com.example.definitionsdefectsbuildingstructures.ui.components.workWithDrawing.image

import android.Manifest
import android.annotation.SuppressLint
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
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.definitionsdefectsbuildingstructures.data.model.Label
import com.example.definitionsdefectsbuildingstructures.ui.components.updateLabel.writeBitmap
import com.example.definitionsdefectsbuildingstructures.ui.screens.workWithDrawing.WorkWithDrawingUiState
import com.example.definitionsdefectsbuildingstructures.ui.screens.workWithDrawing.actions.WorkWithDrawingAction
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import org.apache.commons.io.FileUtils
import java.io.File
import java.util.UUID

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ZoomableImage(
    uiState: WorkWithDrawingUiState,
    isZoomEnabled: Boolean,
    uiAction: (WorkWithDrawingAction) -> Unit,
    onLabelClick: (Label) -> Unit,
) {
    val imageX = remember { mutableStateOf(0f) }
    val imageY = remember { mutableStateOf(0f) }
    val currentPhotoPath = remember { mutableStateOf("") }
    var width = 0f
    var height = 0f
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
                uiState.initialScale.value *= zoom
                uiState.initialOffsetX.value = (uiState.initialOffsetX.value + pan.x)
                uiState.initialOffsetY.value = (uiState.initialOffsetY.value + pan.y)
            }
        }
    } else {
        boxModifier
    }

    val imageModifier = Modifier
        .graphicsLayer(
            scaleX = maxOf(1f, minOf(20f, uiState.initialScale.value)),
            scaleY = maxOf(1f, minOf(20f, uiState.initialScale.value)),
            translationX = uiState.initialOffsetX.value,
            translationY = uiState.initialOffsetY.value
        )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val bitmap = BitmapFactory.decodeFile(currentPhotoPath.value)
            val exif = ExifInterface(currentPhotoPath.value)
            val rotationAngle = when (exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90
                ExifInterface.ORIENTATION_ROTATE_180 -> 180
                ExifInterface.ORIENTATION_ROTATE_270 -> 270
                else -> 0
            }
            val rotatedBitmap = Bitmap.createBitmap(
                bitmap,
                0,
                0,
                bitmap.width,
                bitmap.height,
                Matrix().apply { postRotate(rotationAngle.toFloat()) },
                true
            )
            val fileLabel = uiState.photoNum.toString() + ".jpg"
            uiAction(WorkWithDrawingAction.UpdatePhotoNum(uiState.photoNum + 1))
            val dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString()
            val file = File("$dir/$fileLabel")
            file.writeBitmap(rotatedBitmap, Bitmap.CompressFormat.JPEG, 100)
            val directoryPath = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Checkpoint-K")
            if (!directoryPath.exists()) {
                directoryPath.mkdirs()
            }
            val newFile = File(
                directoryPath,
                uiState.photoNum.toString() + ".jpg"
            )
            FileUtils.copyFile(file, newFile)

            uiAction(
                WorkWithDrawingAction.AddLabel(
                    imageX = imageX.value,
                    imageY = imageY.value,
                    fileName = fileLabel,
                    width = width,
                    height = height
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
                    val strFileName = UUID.randomUUID().toString()
                    val storageDir = context.cacheDir
                    val imgFile = File.createTempFile(strFileName, ".jpg", storageDir)
                    currentPhotoPath.value = imgFile.absolutePath
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    val imageUri = FileProvider.getUriForFile(
                        context,
                        "com.example.definitionsdefectsbuildingstructures.fileprovider",
                        imgFile
                    )
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                    cameraLauncher.launch(cameraIntent)
                    width = size.width.toDp().value
                    height = size.height.toDp().value
                }
            } else permissionState.launchPermissionRequest()
        }
    } else {
        imageModifier
    }

    Box(
        modifier = modifierBox,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = modifierImage
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        LocalContext.current.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                            ?.resolve(uiState.fileName)
                    )
                    .size(Size.ORIGINAL)
                    .build(),
                modifier = Modifier
                    .align(Alignment.Center),
                contentDescription = null,
            )

            uiState.labels.collectAsState().value.forEach { entry ->
                Box(
                    modifier = getModifier(entry = entry, bool = isZoomEnabled, onLabelClick),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = takeLastDigits(entry.fileName),
                        fontSize = 2.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

private fun takeLastDigits(name: String): String {
    val list = name.split(".")
    return if (list[0].length > 1) list[0].takeLast(2)
    else "0" + list[0]
}

@SuppressLint("ModifierFactoryExtensionFunction")
@OptIn(ExperimentalFoundationApi::class)
fun getModifier(entry: Label, bool: Boolean, onLabelClick: (Label) -> Unit): Modifier {
    val clickBoxModifier = Modifier
        .offset(
            x = entry.x.dp - 2.dp,
            y = entry.y.dp - 2.dp
        )
        .size(4.dp)
        .background(
            color = Color.Red,
            shape = CircleShape
        )
        .border(
            width = 0.2.dp,
            color = Color.Black,
            shape = CircleShape
        )

    val clickModifier: Modifier = if (!bool) {
        clickBoxModifier
            .combinedClickable(
                onClick = { },
                onLongClick = { onLabelClick(entry) }
            )
    } else clickBoxModifier
    return clickModifier
}