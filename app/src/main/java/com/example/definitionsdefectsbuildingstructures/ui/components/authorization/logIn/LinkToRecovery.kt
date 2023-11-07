package com.example.definitionsdefectsbuildingstructures.ui.components.authorization.logIn

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.definitionsdefectsbuildingstructures.R

@Composable
fun LinkToRecovery(
    onLinkClick: (Int) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        ClickableText(
            text = AnnotatedString(
                stringResource(id = R.string.recovery_hint)
            ),
            onClick = onLinkClick,
            style = TextStyle(
                color = Blue,
                fontSize = 18.sp,
                textDecoration = TextDecoration.Underline
            )
        )
    }
}