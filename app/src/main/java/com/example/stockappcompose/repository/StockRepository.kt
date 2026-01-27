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

    fun getStocks() = stockLocalDataSource.getStocks()

    fun getOne(id: Int) = stockLocalDataSource.getOne(id)

    fun insertStock(comment: String?, amount: Int?) =
        stockLocalDataSource.insertStock(comment, amount)

    fun updateStock(stock: Stock) =
        stockLocalDataSource.updateStock(stock)

    fun deleteStock(stock: Stock) =
        stockLocalDataSource.deleteStock(stock)

    fun deleteAll() = stockLocalDataSource.deleteAll()
}