package com.example.definitionsdefectsbuildingstructures.ui.components.authorization.recovery

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.definitionsdefectsbuildingstructures.R
import com.example.definitionsdefectsbuildingstructures.ui.components.authorization.generalForAuthorization.CreateButtonForAuthorization
import com.example.definitionsdefectsbuildingstructures.ui.components.authorization.generalForAuthorization.TextFieldForAuthorization

@Composable
fun BoxRecovery(
    onRecoveryClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var phoneNum by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    var allFieldsFilled by remember { mutableStateOf(false) }

    fun checkAllFieldsFilled() {
        allFieldsFilled =
            email.isNotEmpty() && phoneNum.isNotEmpty()
                    && newPassword.isNotEmpty() && repeatPassword.isNotEmpty()

    }

    Column(
        modifier = Modifier
            .border(2.dp, Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextFieldForAuthorization(
            label = stringResource(id = R.string.email),
            text = email,
            typeOfKeyboard = KeyboardType.Email
        ) {
            email = it
            checkAllFieldsFilled()
        }

        TextFieldForAuthorization(
            label = stringResource(id = R.string.phone_number),
            text = phoneNum,
            typeOfKeyboard = KeyboardType.Phone
        ) {
            phoneNum = it
            checkAllFieldsFilled()
        }

        TextFieldForAuthorization(
            label = stringResource(id = R.string.new_password),
            text = newPassword,
            typeOfKeyboard = KeyboardType.Password
        ) {
            newPassword = it
            checkAllFieldsFilled()
        }

        TextFieldForAuthorization(
            label = stringResource(id = R.string.repeat_password),
            text = repeatPassword,
            typeOfKeyboard = KeyboardType.Password
        ) {
            repeatPassword = it
            checkAllFieldsFilled()
        }

        Box(
            modifier = Modifier.padding(10.dp)
        ) {
            CreateButtonForAuthorization(
                text = stringResource(id = R.string.recovering),
                allFieldsFilled,
                onRecoveryClick
            )
        }
    }
}