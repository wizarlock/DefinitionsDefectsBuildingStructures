package com.example.definitionsdefectsbuildingstructures.ui.components.authorization.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.definitionsdefectsbuildingstructures.R

@Preview
@Composable
fun LogoAndName() {
    Column {
        CreateName()
        CreateLogo()
    }
}

@Composable
fun CreateName() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CreateText(
            text = stringResource(id = R.string.app),
            topPad = 30
        )
        CreateText(
            text = stringResource(id = R.string.definition),
            topPad = 0
        )
    }
}

@Composable
fun CreateText(text: String, topPad: Int) {
    Box(
        modifier = Modifier.padding(top = topPad.dp)
    ) {
        Text(
            text = text,
            fontSize = 25.sp,
            color = Color.Black
        )
    }
}

@Composable
fun CreateLogo() {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
                .border(2.dp, Color.Black)
        )
    }
}