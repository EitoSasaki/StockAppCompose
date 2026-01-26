package com.example.stockappcompose.datasource.`interface`

import com.example.stockappcompose.data.db.Stock
import com.example.stockappcompose.data.error.base.BaseError
import com.github.michaelbull.result.Result
import kotlinx.coroutines.flow.Flow

interface StockDataSource {
    fun getStocks(): Flow<Result<List<Stock>, BaseError>>
    fun getOne(id: Int): Flow<Stock?>
    fun insertStock(comment: String?, amount: Int?): Flow<Int>
    fun updateStock(stock: Stock): Flow<Unit>
    fun deleteStock(stock: Stock): Flow<Unit>
}