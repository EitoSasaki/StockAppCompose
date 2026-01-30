package com.example.stockappcompose.ui.layout.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockappcompose.R
import com.example.stockappcompose.data.common.DateFormat
import com.example.stockappcompose.data.ui.Route
import com.example.stockappcompose.data.db.Stock
import com.example.stockappcompose.extension.format
import com.example.stockappcompose.ui.layout.common.CommonImageButton
import com.example.stockappcompose.ui.layout.common.CommonMiddleLabel
import com.example.stockappcompose.ui.layout.common.ImagePicker
import com.example.stockappcompose.viewmodel.StockDetailViewModel

@Composable
fun StockDetailScreen(
    stockDetailViewModel: StockDetailViewModel = viewModel(),
    onPopToScreen: (Route?) -> Unit,
) {
    val stock = stockDetailViewModel.stock.collectAsState().value
    var canOpenImagePicker: Boolean by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        stockDetailViewModel.initView()
    }

    if (canOpenImagePicker) {
        ImagePicker { uri ->
            canOpenImagePicker = false
            stockDetailViewModel.onSelectImage(uri)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = dimensionResource(id = R.dimen.common_space))
    ) {
        ButtonsRow(
            onClickOpenImage = { canOpenImagePicker = true },
            onClickSaveImage = {
                stockDetailViewModel.onClickSaveImage {
                    onPopToScreen(null)
                }
            },
            onClickDeleteImage = {
                stockDetailViewModel.onClickDeleteImage()
            },
        )
        StockDataColumn(stock)
    }
}

@Composable
fun ButtonsRow(
    onClickOpenImage: () -> Unit,
    onClickSaveImage: () -> Unit,
    onClickDeleteImage: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.common_row_height)),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CommonImageButton(
            image = Icons.Default.Add,
            onClick = onClickOpenImage,
        )
        CommonImageButton(
            image = Icons.Default.CheckCircle,
            onClick = onClickSaveImage,
        )
        CommonImageButton(
            image = Icons.Default.Delete,
            onClick = onClickDeleteImage,
        )
    }
}

@Composable
fun StockDataColumn(stock: Stock?) {
    if (stock == null) return
    Column(modifier = Modifier.fillMaxWidth()) {
        CommonMiddleLabel(
            text = stringResource(
                id = R.string.label_create_date,
                stock.createDate.format(DateFormat.HHmmss.format)
            )
        )
        CommonMiddleLabel(text = stringResource(id = R.string.label_amount, stock.amount))
        CommonMiddleLabel(
            text = stringResource(
                id = R.string.label_comment,
                stock.comment.orEmpty()
            )
        )
    }
}