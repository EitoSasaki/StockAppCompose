package com.example.stockappcompose.ui.layout.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.stockappcompose.Constants
import com.example.stockappcompose.R
import com.example.stockappcompose.format
import com.example.stockappcompose.ui.layout.common.CommonButton
import com.example.stockappcompose.ui.layout.common.CommonMiddleLabel
import com.example.stockappcompose.ui.layout.common.CommonTextField
import kotlinx.coroutines.delay
import java.time.LocalDateTime


@Composable
fun StockListScreen() {
    var amount: Int by remember { mutableStateOf(0) }
    val onChangeAmount: (Int) -> Unit = {
        if (it <= Constants.STOCK_AMOUNT_MAX && it >= Constants.STOCK_AMOUNT_MIN) {
            amount = it
        }
    }
    val onClickSubmit: (String) -> Unit = {
        // TODO: リストに行を追加
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        AmountInputRow(
            amount = amount,
            onChangeAmount = onChangeAmount,
        )
        CommentInputRow(
            onClickSubmit = onClickSubmit,
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

@Composable
private fun CommentInputRow(
    onClickSubmit: (String) -> Unit,
) {
    var comment by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CurrentTimer(
            modifier = Modifier.weight(1F),
        )
        CommonTextField(
            modifier = Modifier.weight(2F),
            value = comment,
            placeholder = R.string.label_placeholder_comment,
            onValueChange = { comment = it }
        )
        CommonButton(
            modifier = Modifier.weight(1F),
            btnText = R.string.button_submit,
            onClick = {
                onClickSubmit(comment)
                comment = ""
            }
        )
    }
}

@Composable
private fun CurrentTimer(
    modifier: Modifier = Modifier,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    var canRunTimer by remember { mutableStateOf(false) }
    var currentTime: LocalDateTime by remember { mutableStateOf(LocalDateTime.now()) }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                canRunTimer = true
            } else if (event == Lifecycle.Event.ON_STOP) {
                canRunTimer = false
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(canRunTimer) {
        if (canRunTimer) {
            while (true) {
                currentTime = LocalDateTime.now()
                println(currentTime)
                delay(1000L)
            }
        }
    }

    CommonMiddleLabel(
        modifier = modifier,
        text = currentTime.format(Constants.DATETIME_FORMAT_HHMMSS),
    )
}
