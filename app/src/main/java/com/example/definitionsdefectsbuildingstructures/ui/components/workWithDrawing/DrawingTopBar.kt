package com.example.definitionsdefectsbuildingstructures.ui.components.workWithDrawing

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.definitionsdefectsbuildingstructures.R
import com.example.definitionsdefectsbuildingstructures.ui.components.app.AvatarIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawingTopBar(
    onReloadClick: () -> Unit,
    onExportClick: () -> Unit,
) {
    val context = LocalContext.current
    val text = stringResource(id = R.string.save_progress)
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(
                onClick = { onReloadClick() }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_settings_overscan_black_24dp),
                    contentDescription = "reload",
                    modifier = Modifier
                        .size(32.dp)
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    onExportClick()
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_ios_share_black_24dp),
                    contentDescription = "export",
                    modifier = Modifier
                        .size(32.dp)
                )
            }
        }
    )
}
