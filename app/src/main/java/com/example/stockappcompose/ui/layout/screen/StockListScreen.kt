package com.example.stockappcompose.ui.layout.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.stockappcompose.Constants
import com.example.stockappcompose.R
import com.example.stockappcompose.Stock
import com.example.stockappcompose.format
import com.example.stockappcompose.ui.layout.common.CommonButton
import com.example.stockappcompose.ui.layout.common.CommonMiddleLabel
import com.example.stockappcompose.ui.layout.common.CommonSingleButtonDialog
import com.example.stockappcompose.ui.layout.common.CommonTextField
import com.example.stockappcompose.ui.layout.common.StockListRow
import com.example.stockappcompose.ui.layout.common.StockListRowData
import kotlinx.coroutines.delay
import java.time.LocalDateTime


@Composable
fun StockListScreen() {
    var list: List<StockListRowData> by remember { mutableStateOf(emptyList()) }
    var amount: Int by remember { mutableStateOf(0) }
    var canShowSumDialog: Boolean by remember { mutableStateOf(false) }
    val onChangeAmount: (Int) -> Unit = {
        if (it <= Constants.STOCK_AMOUNT_MAX && it >= Constants.STOCK_AMOUNT_MIN) {
            amount = it
        }
    }
    val onClickSubmit: (String) -> Unit = { comment ->
        list = list.toMutableList().also {
            val newValue = Stock(comment = comment, amount = amount, createDate = LocalDateTime.now())
            it.add(StockListRowData(isChecked = false, stock = newValue))
        }
    }
    val onChangeChecked: (Int, Boolean) -> Unit = { index, isChecked ->
        list = list.toMutableList().also {
            it[index] = it[index].copy(isChecked = isChecked)
        }
    }
    val onClickDelete: (Int) -> Unit = { index ->
        list = list.toMutableList().also {
            it.removeAt(index)
        }
    }
    val onClickClear: () -> Unit = {
        list = list.toMutableList().also {
            it.clear()
        }
    }
    val onClickSum: () -> Unit = {
        canShowSumDialog = true
    }

    if (canShowSumDialog) {
        val sum = list.filter { it.isChecked }.sumOf { it.stock.amount }
        CommonSingleButtonDialog(
            message = stringResource(id = R.string.message_sum, sum),
            btnText = R.string.button_ok,
            onClickButton = { canShowSumDialog = false },
            onDismiss = { canShowSumDialog = false },
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 8.dp)
    ) {
        AmountInputRow(
            amount = amount,
            onChangeAmount = onChangeAmount,
        )
        CommentInputRow(
            onClickSubmit = onClickSubmit,
        )
        StockListColumn(
            modifier = Modifier.weight(1F),
            list = list,
            onChangeChecked = onChangeChecked,
            onClickDelete = onClickDelete,
        )
        BottomButtonRow(
            onClickClear = onClickClear,
            onClickSum = onClickSum,
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
            .height(64.dp),
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
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .background(Color.Transparent)
                .width(8.dp)
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

@Preview
@Composable
private fun AmountInputRowPreview() {
    AmountInputRow(9999) {}
}

@Composable
private fun CommentInputRow(
    onClickSubmit: (String) -> Unit,
) {
    var comment by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
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
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .background(Color.Transparent)
                .width(8.dp)
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

@Preview
@Composable
private fun CommentInputRowPreview() {
    CommentInputRow {}
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

@Composable
private fun StockListColumn(
    modifier: Modifier = Modifier,
    list: List<StockListRowData>,
    onChangeChecked: (Int, Boolean) -> Unit,
    onClickDelete: (Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier
        .fillMaxWidth()
    ) {
        itemsIndexed(list) { index, rowData ->
            StockListRow(
                index = index,
                data = rowData,
                onCheckedChange = {
                    onChangeChecked(index, it)
                },
                onClickDelete = {
                    onClickDelete(index)
                },
            )
        }
    }
}

@Composable
private fun BottomButtonRow(
    onClickClear: () -> Unit,
    onClickSum: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CommonButton(
            modifier = Modifier.weight(2F),
            btnText = R.string.button_clear,
            onClick = onClickClear
        )
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .background(Color.Transparent)
                .width(8.dp)
        )
        CommonButton(
            modifier = Modifier.weight(4F),
            btnText = R.string.button_sum,
            onClick = onClickSum
        )
    }
}

@Preview
@Composable
private fun BottomButtonRowPreview() {
    BottomButtonRow({}, {})
}
