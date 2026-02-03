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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockappcompose.R
import com.example.stockappcompose.data.ui.Route
import com.example.stockappcompose.enumeration.ui.Screen
import com.example.stockappcompose.ui.layout.common.CommonButton
import com.example.stockappcompose.ui.layout.common.CommonMiddleLabel
import com.example.stockappcompose.ui.layout.common.CommonTextField
import com.example.stockappcompose.ui.layout.common.CurrentTimer
import com.example.stockappcompose.ui.layout.common.StockListRow
import com.example.stockappcompose.ui.layout.common.StockListRowData
import com.example.stockappcompose.ui.layout.common.Template
import com.example.stockappcompose.viewmodel.StockListViewModel

@Composable
fun StockListScreen(
    stockListViewModel: StockListViewModel = viewModel(),
    onNavigateToScreen: (Route) -> Unit
) {
    val list = stockListViewModel.list.collectAsState().value
    val amount = stockListViewModel.amount.collectAsState().value

    val onClickRow: (Int) -> Unit = { index ->
        list.getOrNull(index)?.let {
            onNavigateToScreen(Route.StockDetail(it.stock.id))
        }
    }

    LaunchedEffect(Unit) {
        stockListViewModel.initView()
    }

    Template(
        screen = Screen.StockList,
        viewModel = stockListViewModel,
        body = {
            Body(
                list = list,
                amount = amount,
                onChangeAmount = { stockListViewModel.onChangeAmount(it) },
                onClickSubmit = { stockListViewModel.onClickSubmit(it) },
                onClickRow = onClickRow,
                onChangeChecked = { index, isChecked ->
                    stockListViewModel.onChangeChecked(index, isChecked)
                },
                onClickDelete = { stockListViewModel.onClickDelete(it) },
                onClickClear = { stockListViewModel.onClickClear() },
                onClickSum = { stockListViewModel.onClickSum() },
            )
        }
    )
}

@Composable
private fun Body(
    list: List<StockListRowData>,
    amount: Int,
    onChangeAmount: (Int) -> Unit,
    onClickSubmit: (String) -> Unit,
    onClickRow: (Int) -> Unit,
    onChangeChecked: (Int, Boolean) -> Unit,
    onClickDelete: (Int) -> Unit,
    onClickClear: () -> Unit,
    onClickSum: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = dimensionResource(id = R.dimen.common_space))
    ) {
        AmountInputRow(
            amount = amount,
            onChangeAmount = { onChangeAmount(it) },
        )
        CommentInputRow(
            onClickSubmit = { onClickSubmit(it) },
        )
        StockListColumn(
            modifier = Modifier.weight(1F),
            list = list,
            onClickRow = onClickRow,
            onChangeChecked = { index, isChecked -> onChangeChecked(index, isChecked) },
            onClickDelete = { onClickDelete(it) },
        )
        BottomButtonRow(
            onClickClear = { onClickClear() },
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
            .height(dimensionResource(id = R.dimen.common_row_height)),
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
                .width(dimensionResource(id = R.dimen.common_space))
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
            .height(dimensionResource(id = R.dimen.common_row_height)),
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
                .width(dimensionResource(id = R.dimen.common_space))
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
private fun StockListColumn(
    modifier: Modifier = Modifier,
    list: List<StockListRowData>,
    onClickRow: (Int) -> Unit,
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
                onClickRow = {
                    onClickRow(index)
                },
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
            .height(dimensionResource(id = R.dimen.common_row_height)),
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
                .width(dimensionResource(id = R.dimen.common_space))
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
