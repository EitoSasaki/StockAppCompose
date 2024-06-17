package com.example.stockappcompose.ui.layout.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.stockappcompose.Constants
import com.example.stockappcompose.R
import com.example.stockappcompose.ui.layout.common.CommonButton
import com.example.stockappcompose.ui.layout.common.CommonMiddleLabel

@Composable
fun StockListScreen() {
    var amount: Int by remember { mutableStateOf(0) }
    val onChangeAmount: (Int) -> Unit = {
        if (it <= Constants.STOCK_AMOUNT_MAX && it >= Constants.STOCK_AMOUNT_MIN) {
            amount = it
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        AmountInputRow(
            amount = amount,
            onChangeAmount = onChangeAmount,
        )
    }
}

@Composable
private fun AmountInputRow(
    amount: Int,
    onChangeAmount: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CommonMiddleLabel(
            modifier = Modifier.weight(2F),
            text = stringResource(id = R.string.label_amount, amount),
        )
        CommonButton(
            modifier = Modifier.weight(1F),
            btnText = R.string.button_plus,
            onClick = {
                onChangeAmount(amount + 1)
            }
        )
        CommonButton(
            modifier = Modifier.weight(1F),
            btnText = R.string.button_minus,
            onClick = {
                onChangeAmount(amount - 1)
            }
        )
    }
}