package com.example.definitionsdefectsbuildingstructures.ui.components.authorization.logIn

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
import com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.logIn.LogInViewModel
import com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.logIn.actions.LogInUiAction

@Composable
fun BoxLogIn(
    uiState : LogInViewModel.LogInUiState,
    uiStateBoolean: LogInViewModel.LogInUiStateBoolean,
    uiAction: (LogInUiAction) -> Unit,
    onEnteringClick: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .border(2.dp, Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextFieldForAuthorization(
            label = stringResource(id = R.string.email),
            text = uiState.email,
            isValid = uiStateBoolean.email,
            onValueChange = { text ->
                uiAction(LogInUiAction.UpdateEmail(text))
            },
            typeOfKeyboard = KeyboardType.Email
        )
        
        if (!uiStateBoolean.email)
            Text(
                text = stringResource(id = R.string.incorrect_email),
                fontSize = 10.sp,
                color = Color.Red
            )

        TextFieldForAuthorization(
            label = stringResource(id = R.string.password),
            text = uiState.password,
            isValid = uiStateBoolean.password,
            onValueChange = { text ->
                uiAction(LogInUiAction.UpdatePassword(text))
            },
            typeOfKeyboard = KeyboardType.Password
        )

        if (!uiStateBoolean.password)
            Text(
                text = stringResource(id = R.string.incorrect_password),
                fontSize = 10.sp,
                color = Color.Red
            )

        Box(
            modifier = Modifier.padding(10.dp)
        ) {
            CreateButtonForAuthorization(
                text = stringResource(id = R.string.entering),
                onEnteringClick
            )
        }
    }
}

