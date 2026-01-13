package com.example.stockappcompose.repository

import com.example.stockappcompose.Stock
import com.example.stockappcompose.datasource.StockLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StockRepository @Inject constructor(
    private val stockLocalDataSource: StockLocalDataSource
) {

    fun getStocks(): Flow<List<Stock>> = stockLocalDataSource.getStocks()
}