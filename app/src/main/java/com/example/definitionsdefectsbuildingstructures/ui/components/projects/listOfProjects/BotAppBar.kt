package com.example.definitionsdefectsbuildingstructures.ui.components.projects.listOfProjects

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.definitionsdefectsbuildingstructures.R

@Composable
fun MyBotAppBar(onAddClick: () -> Unit) {
    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = Color.White,
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CreateLogoForBot()
                CreateNameForBot()
                CreateBotActionButton(onAddClick)
            }
        }

    )
}


@Composable
fun CreateLogoForBot() {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxHeight(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo"
        )
    }
}

@Composable
fun CreateNameForBot() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CreateTextForBot(
            text = stringResource(id = R.string.app),
        )
        CreateTextForBot(
            text = stringResource(id = R.string.definition),
        )
    }
}

@Composable
fun CreateTextForBot(text: String) {
    Text(
        text = text,
        fontSize = 18.sp,
        color = Color.Black
    )
}

@Composable
fun CreateBotActionButton(onAddClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onAddClick() },
        modifier = Modifier

            .padding(5.dp),
        shape = CircleShape,
        containerColor = Color.White,
        contentColor = Color.Black
    ) {
        Icon(Icons.Rounded.Add, contentDescription = null)
    }
}