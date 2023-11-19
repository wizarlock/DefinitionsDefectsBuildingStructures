package com.example.definitionsdefectsbuildingstructures.ui.components.updateLabel

import android.os.Environment
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun LabelImage(fileName: String) {
    Box(
        modifier = Modifier
            .clip(RectangleShape)
            .fillMaxWidth()
            .fillMaxHeight(0.75f)
            .border(2.dp, Color.Black)
            .background(Color.White)
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
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
    }
}