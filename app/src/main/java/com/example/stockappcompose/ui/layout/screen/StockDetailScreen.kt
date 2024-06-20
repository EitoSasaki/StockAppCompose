package com.example.stockappcompose.ui.layout.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockappcompose.Constants
import com.example.stockappcompose.R
import com.example.stockappcompose.Stock
import com.example.stockappcompose.format
import com.example.stockappcompose.ui.layout.common.CommonMiddleLabel
import com.example.stockappcompose.viewmodel.StockDetailViewModel

@Composable
fun StockDetailScreen(
    stockDetailViewModel: StockDetailViewModel = viewModel()
) {
    val stock = stockDetailViewModel.stock.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = dimensionResource(id = R.dimen.common_space))
    ) {
        StockDataColumn(stock)
    }
}

@Composable
fun StockDataColumn(stock: Stock?) {
    if (stock == null) return
    Column(modifier = Modifier.fillMaxWidth()) {
        CommonMiddleLabel(text = stringResource(id = R.string.label_create_date, stock.createDate.format(Constants.DATETIME_FORMAT_HHMMSS)))
        CommonMiddleLabel(text = stringResource(id = R.string.label_amount, stock.amount))
        CommonMiddleLabel(text = stringResource(id = R.string.label_comment, stock.comment.orEmpty()))
    }
}