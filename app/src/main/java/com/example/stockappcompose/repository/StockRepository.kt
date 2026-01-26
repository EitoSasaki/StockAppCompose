package com.example.stockappcompose.repository

import com.example.stockappcompose.data.db.Stock
import com.example.stockappcompose.data.error.base.BaseError
import com.example.stockappcompose.datasource.StockLocalDataSource
import com.github.michaelbull.result.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StockRepository @Inject constructor(
    private val stockLocalDataSource: StockLocalDataSource
) {

    fun getStocks(): Flow<Result<List<Stock>, BaseError>> = stockLocalDataSource.getStocks()

    fun getOne(id: Int): Flow<Stock?> = stockLocalDataSource.getOne(id)

    fun insertStock(comment: String?, amount: Int?): Flow<Int> =
        stockLocalDataSource.insertStock(comment, amount)

    fun updateStock(stock: Stock): Flow<Unit> =
        stockLocalDataSource.updateStock(stock)

    fun deleteStock(stock: Stock): Flow<Unit> =
        stockLocalDataSource.deleteStock(stock)
}