package com.example.stockappcompose.ui.layout.common

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun CommonTextField(
    modifier: Modifier = Modifier,
    value: String,
    @StringRes placeholder: Int = 0,
    onValueChange: (String) -> Unit,
) {
    Column(modifier = modifier) {
        BasicTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            maxLines = 1,
            onValueChange = onValueChange,
            decorationBox = {
                if (value.isEmpty()) {
                    CommonMiddleLabel(text = stringResource(id = placeholder))
                } else {
                    CommonMiddleLabel(text = value)
                }
            }
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .height(1.dp)
        )
    }
}