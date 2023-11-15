package com.example.definitionsdefectsbuildingstructures.ui.components.drawings.drawingsList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.definitionsdefectsbuildingstructures.R
import com.example.definitionsdefectsbuildingstructures.data.model.DrawingItem

@Composable
fun SelectDrawing(
    list: List<DrawingItem>,
    paddingValues: PaddingValues,
    onDrawingClick: (DrawingItem) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(paddingValues)
            .padding(start = 20.dp, end = 20.dp)
    ) {
        Column {
            OutlinedTextField(
                value = if (list.isEmpty()) stringResource(id = R.string.drawings_empty) else stringResource(
                    id = R.string.select_drawing
                ),
                onValueChange = { },
                modifier = Modifier.requiredWidth(300.dp),
                trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
                readOnly = true,
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                ),
                textStyle = TextStyle(fontSize = 25.sp)
            )
            DropdownMenu(
                expanded = expanded && list.isNotEmpty(),
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .requiredWidth(300.dp)
                    .heightIn(max = 370.dp)
                ) {
                list.forEach { entry ->
                    DropdownMenuItem(
                        modifier = Modifier.requiredWidth(300.dp),
                        onClick = {
                            expanded = false
                            onDrawingClick(entry)
                        },
                        text = {
                            Text(
                                text = entry.name,
                                fontSize = 25.sp,
                                modifier = Modifier
                                    .align(Alignment.Start)

                            )
                        }
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .padding(10.dp)
                .clickable(
                    onClick = { expanded = !expanded }
                )
        )
    }
}
