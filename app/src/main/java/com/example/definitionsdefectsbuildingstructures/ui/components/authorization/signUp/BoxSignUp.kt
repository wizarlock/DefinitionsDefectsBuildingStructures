package com.example.definitionsdefectsbuildingstructures.ui.components.authorization.signUp

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
import com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.signUp.SignUpViewModel
import com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.signUp.actions.SignUpAction

@Composable
fun BoxSignUp(
    uiState: SignUpViewModel.SignUpUiState,
    uiStateBoolean: SignUpViewModel.SignUpUiStateBoolean,
    uiAction: (SignUpAction) -> Unit,
    onRegistrationClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .border(2.dp, Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextFieldForAuthorization(
            label = stringResource(id = R.string.surname),
            text = uiState.surname,
            isValid = uiStateBoolean.surname,
            onValueChange = { text ->
                uiAction(SignUpAction.UpdateSurname(text))
            },
            typeOfKeyboard = KeyboardType.Text
        )

        if (!uiStateBoolean.surname)
            Text(
                text = stringResource(id = R.string.incorrect_surname),
                fontSize = 10.sp,
                color = Color.Red
            )

        TextFieldForAuthorization(
            label = stringResource(id = R.string.name),
            text = uiState.name,
            isValid = uiStateBoolean.name,
            onValueChange = { text ->
                uiAction(SignUpAction.UpdateName(text))
            },
            typeOfKeyboard = KeyboardType.Text
        )

        if (!uiStateBoolean.name)
            Text(
                text = stringResource(id = R.string.incorrect_name),
                fontSize = 10.sp,
                color = Color.Red
            )

        TextFieldForAuthorization(
            label = stringResource(id = R.string.patronymic),
            text = uiState.patronymic,
            isValid = uiStateBoolean.patronymic,
            onValueChange = { text ->
                uiAction(SignUpAction.UpdatePatronymic(text))
            },
            typeOfKeyboard = KeyboardType.Text
        )

        if (!uiStateBoolean.patronymic)
            Text(
                text = stringResource(id = R.string.incorrect_patronymic),
                fontSize = 10.sp,
                color = Color.Red
            )

        TextFieldForAuthorization(
            label = stringResource(id = R.string.email),
            text = uiState.email,
            isValid = uiStateBoolean.email,
            onValueChange = { text ->
                uiAction(SignUpAction.UpdateEmail(text))
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
            label = stringResource(id = R.string.phone_number),
            text = uiState.phoneNum,
            isValid = uiStateBoolean.phoneNum,
            onValueChange = { text ->
                uiAction(SignUpAction.UpdatePhoneNum(text))
            },
            typeOfKeyboard = KeyboardType.Phone
        )

        if (!uiStateBoolean.phoneNum)
            Text(
                text = stringResource(id = R.string.incorrect_phoneNum),
                fontSize = 10.sp,
                color = Color.Red
            )

        TextFieldForAuthorization(
            label = stringResource(id = R.string.password),
            text = uiState.password,
            isValid = uiStateBoolean.password,
            onValueChange = { text ->
                uiAction(SignUpAction.UpdatePassword(text))
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
                text = stringResource(id = R.string.registration),
                onRegistrationClick
            )
        }
    }
}