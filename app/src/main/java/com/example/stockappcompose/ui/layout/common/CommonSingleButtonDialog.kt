package com.example.stockappcompose.ui.layout.common

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.stockappcompose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonSingleButtonDialog(
    message: String,
    @StringRes btnText: Int,
    onClickButton: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        content = {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(dimensionResource(id = R.dimen.common_space)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CommonMiddleLabel(text = message)
                Spacer(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .height(dimensionResource(id = R.dimen.common_space))
                )
                CommonButton(btnText = btnText, onClick = onClickButton)
            }
        }
    )
}

@Preview
@Composable
private fun CommonSingleButtonDialogPreview() {
    CommonSingleButtonDialog("サンプル", R.string.button_ok, {}, {})
}