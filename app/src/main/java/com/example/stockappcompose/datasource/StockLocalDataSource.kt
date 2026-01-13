package com.example.stockappcompose.datasource

import com.example.stockappcompose.Stock
import com.example.stockappcompose.datasource.`interface`.StockDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class StockLocalDataSource @Inject constructor() : StockDataSource {

    override fun getStocks(): Flow<List<Stock>> {
        // TODO: データベース実装時に追加
        return flowOf(emptyList())
    }
}