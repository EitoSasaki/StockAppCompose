package com.example.stockappcompose.datasource.`interface`

import com.example.stockappcompose.Stock
import kotlinx.coroutines.flow.Flow

interface StockDataSource {
    fun getStocks(): Flow<List<Stock>>
}