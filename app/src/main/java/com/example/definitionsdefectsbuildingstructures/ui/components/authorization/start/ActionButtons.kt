package com.example.definitionsdefectsbuildingstructures.ui.components.authorization.start

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.definitionsdefectsbuildingstructures.R
import com.example.definitionsdefectsbuildingstructures.ui.components.authorization.generalForAuthorization.CreateButtonForAuthorization

@Preview
@Composable
fun ActionButtons(
    onLogInClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CreateButtonForAuthorization(
            text = stringResource(id = R.string.logIn),
            onLogInClick
        )
        CreateButtonForAuthorization(
            text = stringResource(id = R.string.signUp),
            onSignUpClick
        )
    }
}
