package com.example.stockappcompose.ui.layout.common

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@SuppressLint("SupportAnnotationUsage")
@Composable
fun CommonButton(
    modifier: Modifier = Modifier,
    @StringRes btnText: Int,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        content = {
            CommonSmallLabel(text = stringResource(id = btnText))
        },
    )
}