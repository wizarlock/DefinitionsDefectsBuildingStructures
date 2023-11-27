package com.example.definitionsdefectsbuildingstructures.ui.components.workWithDrawing


import android.Manifest
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.definitionsdefectsbuildingstructures.R
import com.example.definitionsdefectsbuildingstructures.ui.screens.workWithDrawing.WorkWithDrawingUiState
import com.example.definitionsdefectsbuildingstructures.ui.screens.workWithDrawing.actions.WorkWithDrawingAction
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AudioButton(
    uiAction: (WorkWithDrawingAction) -> Unit,
    uiState: WorkWithDrawingUiState
) {
    var clickCount by remember { mutableStateOf(0) }
    val permissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)
    val context = LocalContext.current
    val start = stringResource(id = R.string.audio_start)
    val stop = stringResource(id = R.string.audio_stop)

    Button(
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        onClick = {
            if (permissionState.hasPermission) {
                clickCount++
                if (clickCount % 2 != 0) {
                    uiAction(WorkWithDrawingAction.StartRecord(uiState.audioNum.toString()))
                    uiAction(WorkWithDrawingAction.UpdateAudioNum(uiState.audioNum + 1))
                    Toast.makeText(context, start, Toast.LENGTH_SHORT).show()
                } else {
                    uiAction(WorkWithDrawingAction.StopRecord)
                    Toast.makeText(context, stop, Toast.LENGTH_SHORT).show()
                }
            } else {
                permissionState.launchPermissionRequest()
            }
        },
        shape = CircleShape,
        border = BorderStroke(2.dp, Color.Black),
        contentPadding = PaddingValues(25.dp),
        modifier = Modifier.padding(end = 10.dp)
    ) {
        Text(
            text = stringResource(id = R.string.audio_mode) + "\n" +
                    if (clickCount % 2 == 0) stringResource(R.string.audio_mode_off) else stringResource(
                        R.string.audio_mode_on
                    ),
            fontSize = 20.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}