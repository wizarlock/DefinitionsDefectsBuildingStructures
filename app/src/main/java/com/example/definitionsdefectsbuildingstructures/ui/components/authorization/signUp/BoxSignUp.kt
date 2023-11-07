package com.example.definitionsdefectsbuildingstructures.ui.components.authorization.signUp

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
fun BoxSignUp(
    onRegistrationClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var phoneNum by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var patronymic by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var allFieldsFilled by remember { mutableStateOf(false) }

    fun checkAllFieldsFilled() {
        allFieldsFilled =
            email.isNotEmpty() && password.isNotEmpty() && phoneNum.isNotEmpty()
                    && name.isNotEmpty() && surname.isNotEmpty() && patronymic.isNotEmpty()
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
            label = stringResource(id = R.string.name),
            text = name,
            typeOfKeyboard = KeyboardType.Text
        ) {
            name = it
            checkAllFieldsFilled()
        }

        TextFieldForAuthorization(
            label = stringResource(id = R.string.surname),
            text = surname,
            typeOfKeyboard = KeyboardType.Text
        ) {
            surname = it
            checkAllFieldsFilled()
        }

        TextFieldForAuthorization(
            label = stringResource(id = R.string.patronymic),
            text = patronymic,
            typeOfKeyboard = KeyboardType.Text
        ) {
            patronymic = it
            checkAllFieldsFilled()
        }

        TextFieldForAuthorization(
            label = stringResource(id = R.string.password),
            text = password,
            typeOfKeyboard = KeyboardType.Password
        ) {
            password = it
            checkAllFieldsFilled()
        }
        Box(
            modifier = Modifier.padding(10.dp)
        ) {
            CreateButtonForAuthorization(
                text = stringResource(id = R.string.registration),
                allFieldsFilled,
                onRegistrationClick
            )
        }
    }
}