package com.example.stockappcompose.datasource.`interface`

import com.example.stockappcompose.data.db.Stock
import com.example.stockappcompose.data.error.base.BaseError
import com.github.michaelbull.result.Result
import kotlinx.coroutines.flow.Flow

interface StockDataSource {
    fun getStocks(): Flow<Result<List<Stock>, BaseError>>
    fun getOne(id: Int): Flow<Result<Stock?, BaseError>>
    fun insertStock(comment: String?, amount: Int?): Flow<Result<Int, BaseError>>
    fun updateStock(stock: Stock): Flow<Result<Unit, BaseError>>
    fun deleteStock(stock: Stock): Flow<Result<Unit, BaseError>>
    fun deleteAll(): Flow<Result<Unit, BaseError>>
}