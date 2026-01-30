package com.example.stockappcompose.ui.layout.common

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.stockappcompose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleButtonErrorDialog(
    message: String,
    code: Int,
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
                Row(modifier = Modifier.fillMaxWidth()) {
                    CommonMiddleLabel(text = stringResource(R.string.label_error))
                    CommonSmallLabel(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = code.toString()
                    )
                }
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
private fun SingleButtonErrorDialogPreview() {
    SingleButtonErrorDialog("サンプル", 1000, R.string.button_ok, {}, {})
}