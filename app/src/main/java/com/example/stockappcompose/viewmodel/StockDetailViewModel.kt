package com.example.stockappcompose.viewmodel

import androidx.lifecycle.ViewModel
import com.example.stockappcompose.Stock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StockDetailViewModel(stock: Stock) : ViewModel() {

    private val _stock = MutableStateFlow<Stock?>(null)

    val stock: StateFlow<Stock?> = _stock

    init {
        _stock.value = stock
    }
}