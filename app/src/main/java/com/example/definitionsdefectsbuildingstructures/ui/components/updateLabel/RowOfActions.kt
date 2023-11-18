package com.example.definitionsdefectsbuildingstructures.ui.components.updateLabel

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
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
import com.example.definitionsdefectsbuildingstructures.R
import com.example.definitionsdefectsbuildingstructures.ui.screens.updateLabel.actions.UpdateLabelAction
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import java.io.FileOutputStream
import java.io.IOException
import java.util.UUID


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RowOfActions(
    onDeleteClick: () -> Unit,
    uiAction: (UpdateLabelAction) -> Unit,
    ) {
    val permissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val context = LocalContext.current

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val bitmap = data?.extras?.get("data") as Bitmap
            val fileLabel = UUID.randomUUID().toString() + ".jpg"
            uiAction(UpdateLabelAction.UpdatePhoto(fileLabel))
            val dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString()
            saveBitmapToFile(bitmap, fileLabel, dir)
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
                        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        cameraLauncher.launch(cameraIntent)
                    } else permissionState.launchPermissionRequest()
                },
                color = Color.Black,
                style = TextStyle(textDecoration = TextDecoration.Underline)

            )
        }
    }
}

fun saveBitmapToFile(bitmap: Bitmap, filename: String, dir: String) {
    val imageFile = "$dir/$filename"
    try {
        val outputStream = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}