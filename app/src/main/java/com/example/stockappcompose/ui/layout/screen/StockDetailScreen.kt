package com.example.stockappcompose.ui.layout.screen

import android.net.Uri
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockappcompose.R
import com.example.stockappcompose.data.db.Stock
import com.example.stockappcompose.data.ui.Route
import com.example.stockappcompose.enumeration.common.DateFormat
import com.example.stockappcompose.enumeration.ui.Screen
import com.example.stockappcompose.extension.format
import com.example.stockappcompose.ui.layout.common.CommonImageButton
import com.example.stockappcompose.ui.layout.common.CommonMiddleLabel
import com.example.stockappcompose.ui.layout.common.Template
import com.example.stockappcompose.viewmodel.StockDetailViewModel

@Composable
fun StockDetailScreen(
    stockDetailViewModel: StockDetailViewModel = viewModel(),
    onPopToScreen: (Route?) -> Unit,
) {
    val stock = stockDetailViewModel.stock.collectAsState().value
    val selectedImage = stockDetailViewModel.selectedImage.collectAsState().value

    LaunchedEffect(Unit) {
        stockDetailViewModel.initView()
    }

    Template(
        screen = Screen.StockDetail,
        viewModel = stockDetailViewModel,
        body = {
            Body(
                stock = stock,
                selectedImage = selectedImage,
                onClickOpenImage = { stockDetailViewModel.onClickOpenImage() },
                onClickSaveImage = {
                    stockDetailViewModel.onClickSaveImage {
                        onPopToScreen(null)
                    }
                },
                onClickDeleteImage = {
                    stockDetailViewModel.onClickDeleteImage()
                },
            )
        }
    )
}

@Composable
private fun Body(
    stock: Stock?,
    selectedImage: Uri?,
    onClickOpenImage: () -> Unit,
    onClickSaveImage: () -> Unit,
    onClickDeleteImage: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = dimensionResource(id = R.dimen.common_space))
    ) {
        ButtonsRow(
            onClickOpenImage = onClickOpenImage,
            onClickSaveImage = onClickSaveImage,
            onClickDeleteImage = onClickDeleteImage,
        )
        StockDataColumn(stock, selectedImage)
    }
}

@Composable
private fun ButtonsRow(
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
private fun StockDataColumn(stock: Stock?, selectedImage: Uri?) {
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