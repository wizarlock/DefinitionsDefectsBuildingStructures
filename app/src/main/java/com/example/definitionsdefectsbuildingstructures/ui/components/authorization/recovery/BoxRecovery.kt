package com.example.definitionsdefectsbuildingstructures.ui.components.authorization.recovery

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.definitionsdefectsbuildingstructures.R
import com.example.definitionsdefectsbuildingstructures.ui.components.authorization.generalForAuthorization.CreateButtonForAuthorization
import com.example.definitionsdefectsbuildingstructures.ui.components.authorization.generalForAuthorization.TextFieldForAuthorization
import com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.recovery.RecoveryViewModel
import com.example.definitionsdefectsbuildingstructures.ui.screens.authorization.recovery.actions.RecoveryAction

@Composable
fun BoxRecovery(
    uiState : RecoveryViewModel.RecoveryUiState,
    uiAction: (RecoveryAction) -> Unit,
    onRecoveryClick: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .border(2.dp, Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextFieldForAuthorization(
            label = stringResource(id = R.string.email),
            text = uiState.email,
            onValueChange = { text ->
                uiAction(RecoveryAction.UpdateEmail(text))
            },
            typeOfKeyboard = KeyboardType.Email
        )

        TextFieldForAuthorization(
            label = stringResource(id = R.string.phone_number),
            text = uiState.phoneNum,
            onValueChange = { text ->
                uiAction(RecoveryAction.UpdatePhoneNum(text))
            },
            typeOfKeyboard = KeyboardType.Phone
        )

        TextFieldForAuthorization(
            label = stringResource(id = R.string.new_password),
            text = uiState.password,
            onValueChange = { text ->
                uiAction(RecoveryAction.UpdatePassword(text))
            },
            typeOfKeyboard = KeyboardType.Password
        )

        TextFieldForAuthorization(
            label = stringResource(id = R.string.repeat_password),
            text = uiState.repeatPassword,
            onValueChange = { text ->
                uiAction(RecoveryAction.UpdateRepeatPassword(text))
            },
            typeOfKeyboard = KeyboardType.Password
        )

        Box(
            modifier = Modifier.padding(10.dp)
        ) {
            CreateButtonForAuthorization(
                text = stringResource(id = R.string.recovering),
                true,
                onRecoveryClick
            )
        }
    }
}