package com.example.definitionsdefectsbuildingstructures.ui.components.updateLabel

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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.example.definitionsdefectsbuildingstructures.R
import com.example.definitionsdefectsbuildingstructures.ui.screens.updateLabel.actions.UpdateLabelAction
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File
import java.util.UUID


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RowOfActions(
    onDeleteClick: () -> Unit,
    uiAction: (UpdateLabelAction) -> Unit,
    ) {
    val permissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val context = LocalContext.current
    var currentPhotoPath = ""

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val bitmap = BitmapFactory.decodeFile(currentPhotoPath)
        if (result.resultCode == Activity.RESULT_OK) {
            val exif = ExifInterface(currentPhotoPath)
            val rotationAngle = when (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90
                ExifInterface.ORIENTATION_ROTATE_180 -> 180
                ExifInterface.ORIENTATION_ROTATE_270 -> 270
                else -> 0
            }
            val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, Matrix().apply { postRotate(rotationAngle.toFloat()) }, true)
            val fileLabel = UUID.randomUUID().toString() + ".jpg"
            uiAction(UpdateLabelAction.UpdatePhoto(fileLabel))
            val dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString()
            val file = File("$dir/$fileLabel")
            file.writeBitmap(rotatedBitmap, Bitmap.CompressFormat.JPEG, 100)
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = stringResource(id = R.string.delete_label),
                fontSize = 20.sp,
                modifier = Modifier
                    .clickable { onDeleteClick() },
                color = Color.Red,
                style = TextStyle(textDecoration = TextDecoration.Underline)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd

        ) {
            Text(
                text = stringResource(id = R.string.change_photo),
                fontSize = 20.sp,
                modifier = Modifier.clickable {
                    if (permissionState.hasPermission) {
                        val strFileName = "photo"
                        val storageDir = context.cacheDir
                        val imgFile = File.createTempFile(strFileName, ".jpg", storageDir)
                        currentPhotoPath = imgFile.absolutePath
                        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        val imageUri = FileProvider.getUriForFile(context, "com.example.definitionsdefectsbuildingstructures.fileprovider", imgFile)
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                        cameraLauncher.launch(cameraIntent)
                    } else permissionState.launchPermissionRequest()
                },
                color = Color.Black,
                style = TextStyle(textDecoration = TextDecoration.Underline)

            )
        }
    }
}
fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
    outputStream().use { out ->
        bitmap.compress(format, quality, out)
        out.flush()
    }
}