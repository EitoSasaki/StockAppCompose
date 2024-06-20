package com.example.stockappcompose.viewmodel

import androidx.lifecycle.ViewModel
import com.example.stockappcompose.Constants
import com.example.stockappcompose.Stock
import com.example.stockappcompose.ui.layout.common.StockListRowData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDateTime

class StockListViewModel : ViewModel() {

    private val _list = MutableStateFlow<List<StockListRowData>>(emptyList())
    private val _amount = MutableStateFlow(0)

    val list: StateFlow<List<StockListRowData>> = _list
    val amount: StateFlow<Int> = _amount
    
    fun onChangeAmount(newValue: Int) {
        if (newValue <= Constants.STOCK_AMOUNT_MAX && newValue >= Constants.STOCK_AMOUNT_MIN) {
            _amount.value = newValue
        }
    }

    fun onClickSubmit(comment: String) {
        _list.value = _list.value.toMutableList().also {
            val newValue = Stock(
                comment = comment.ifBlank { null },
                amount = _amount.value,
                createDate = LocalDateTime.now()
            )
            it.add(StockListRowData(isChecked = false, stock = newValue))
        }
    }

    fun onChangeChecked(index: Int, isChecked: Boolean) {
        _list.value = _list.value.toMutableList().also {
            it[index] = it[index].copy(isChecked = isChecked)
        }
    }

    fun onClickDelete(index: Int) {
        _list.value = _list.value.toMutableList().also {
            it.removeAt(index)
        }
    }

    fun onClickClear() {
        _list.value = _list.value.toMutableList().also {
            it.clear()
        }
    }
}