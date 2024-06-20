package com.example.stockappcompose.ui.layout.common

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.stockappcompose.R

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Black,
        ),
        onClick = onClick,
        content = {
            CommonMiddleLabel(text = stringResource(id = R.string.button_back))
        },
    )
}