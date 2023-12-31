package com.example.definitionsdefectsbuildingstructures.ui.components.projects.addProject

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
import com.example.definitionsdefectsbuildingstructures.ui.screens.projects.addProject.AddProjectViewModel
import com.example.definitionsdefectsbuildingstructures.ui.screens.projects.addProject.actions.AddProjectAction

@Composable
fun BoxAddProject(
    uiState: AddProjectViewModel.AddProjectUiState,
    uiStateBoolean: AddProjectViewModel.AddProjectUiStateBoolean,
    uiAction: (AddProjectAction) -> Unit,
    onSavingClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .border(2.dp, Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextFieldForAuthorization(
            label = stringResource(id = R.string.project_name),
            text = uiState.projectName,
            isValid = uiStateBoolean.projectName,
            onValueChange = { text ->
                uiAction(AddProjectAction.UpdateProjectName(text))
            },
            typeOfKeyboard = KeyboardType.Text
        )

        if (!uiStateBoolean.projectName)
            Text(
                text = stringResource(id = R.string.incorrect_project_name),
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

