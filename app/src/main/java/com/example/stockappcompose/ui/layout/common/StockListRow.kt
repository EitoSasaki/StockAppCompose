package com.example.stockappcompose.ui.layout.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.stockappcompose.domain.Constants
import com.example.stockappcompose.R
import com.example.stockappcompose.data.common.DateFormat
import com.example.stockappcompose.data.db.Stock
import com.example.stockappcompose.extension.format
import java.time.LocalDateTime

data class StockListRowData(
    var isChecked: Boolean = false,
    val stock: Stock,
)

@Composable
fun StockListRow(
    index: Int,
    data: StockListRowData,
    onClickRow: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    onClickDelete: () -> Unit,
) {
    val backgroundColor = when {
        data.isChecked -> Color.Green
        index % 2 != 0 -> Color.Cyan
        else -> Color.White
    }
    Row(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.common_row_height))
            .padding(horizontal = dimensionResource(id = R.dimen.common_space))
            .clickable(onClick = onClickRow),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(checked = data.isChecked, onCheckedChange = onCheckedChange)
        CommonSmallLabel(
            modifier = Modifier.weight(2F),
            text = data.stock.createDate.format(DateFormat.HHmmss.format)
        )
        CommonSmallLabel(
            modifier = Modifier.weight(1F),
            text = data.stock.amount.toString()
        )
        CommonSmallLabel(
            modifier = Modifier.weight(3F),
            text = data.stock.comment.orEmpty()
        )
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .background(Color.Transparent)
                .width(dimensionResource(id = R.dimen.common_space))
        )
        CommonButton(
            modifier = Modifier.width(dimensionResource(id = R.dimen.button_min_width)),
            btnText = R.string.button_delete,
            onClick = onClickDelete
        )
    }
}

@Preview
@Composable
private fun StockListRowPreview() {
    val rowData = StockListRowData(isChecked = false, Stock(0, "コメント", 9999, null, LocalDateTime.now()))
    StockListRow(1, rowData, {}, {}, {})
}