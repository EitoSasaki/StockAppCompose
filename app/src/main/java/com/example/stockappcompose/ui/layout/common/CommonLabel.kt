package com.example.stockappcompose.ui.layout.common

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType

@Composable
fun CommonMiddleLabel(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = modifier,
        fontSize = TextUnit(value = 16F, TextUnitType.Sp),
        text = text,
    )
}

@Composable
fun CommonSmallLabel(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = modifier,
        fontSize = TextUnit(value = 16F, TextUnitType.Sp),
        text = text,
    )
}

@Preview
@Composable
private fun CommonLabelPreview() {
    Column {
        CommonSmallLabel(text = "1234567789")
        CommonMiddleLabel(text = "1234567789")
    }
}