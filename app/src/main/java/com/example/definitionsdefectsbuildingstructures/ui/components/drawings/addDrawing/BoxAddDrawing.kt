package com.example.definitionsdefectsbuildingstructures.ui.components.drawings.addDrawing

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.definitionsdefectsbuildingstructures.R
import com.example.definitionsdefectsbuildingstructures.ui.components.authorization.generalForAuthorization.CreateButtonForAuthorization
import com.example.definitionsdefectsbuildingstructures.ui.components.authorization.generalForAuthorization.TextFieldForAuthorization
import com.example.definitionsdefectsbuildingstructures.ui.screens.drawings.addDrawing.AddDrawingViewModel
import com.example.definitionsdefectsbuildingstructures.ui.screens.drawings.addDrawing.actions.AddDrawingAction


@Composable
fun BoxAddDrawing(
    uiState: AddDrawingViewModel.AddDrawingUiState,
    uiStateBoolean: AddDrawingViewModel.AddDrawingUiStateBoolean,
    uiAction: (AddDrawingAction) -> Unit,
    onSavingClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .border(2.dp, Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextFieldForAuthorization(
            label = stringResource(id = R.string.drawing_name),
            text = uiState.drawingName,
            isValid = uiStateBoolean.drawingName,
            onValueChange = { text ->
                uiAction(AddDrawingAction.UpdateDrawingName(text))
            },
            typeOfKeyboard = KeyboardType.Text
        )

        if (!uiStateBoolean.drawingName)
            Text(
                text = stringResource(id = R.string.incorrect_drawing_name),
                fontSize = 10.sp,
                color = Color.Red
            )

        ClickableTextField(
            loadFile = { uri ->
                uiAction(AddDrawingAction.UpdateSelectedFile(uri))
            },
            isValid = uiStateBoolean.isFileExists,
            isFilled = uiState.drawingFileUri != null
        )

        if (!uiStateBoolean.isFileExists)
            Text(
                text = stringResource(id = R.string.incorrect_drawing_file),
                fontSize = 10.sp,
                color = Color.Red
            )

        Box(
            modifier = Modifier.padding(10.dp)
        ) {
            CreateButtonForAuthorization(
                text = stringResource(id = R.string.save), onSavingClick
            )
        }
    }
}