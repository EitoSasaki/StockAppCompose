package com.example.stockappcompose.repository

import com.example.stockappcompose.data.db.Stock
import com.example.stockappcompose.datasource.StockLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StockRepository @Inject constructor(
    private val stockLocalDataSource: StockLocalDataSource
) {

    fun getStocks(): Flow<List<Stock>> = stockLocalDataSource.getStocks()

    fun getOne(id: Int): Flow<Stock?> = stockLocalDataSource.getOne(id)

    fun insertStock(comment: String?, amount: Int?): Flow<Int> =
        stockLocalDataSource.insertStock(comment, amount)

    fun updateStock(stock: Stock): Flow<Unit> =
        stockLocalDataSource.updateStock(stock)

    fun deleteStock(stock: Stock): Flow<Unit> =
        stockLocalDataSource.deleteStock(stock)
}