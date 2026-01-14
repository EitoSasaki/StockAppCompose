package com.example.stockappcompose.datasource

import androidx.room.withTransaction
import com.example.stockappcompose.AppDatabase
import com.example.stockappcompose.data.db.Stock
import com.example.stockappcompose.data.db.dao.StockDao
import com.example.stockappcompose.datasource.`interface`.StockDataSource
import com.example.stockappcompose.orZero
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDateTime
import javax.inject.Inject

class StockLocalDataSource @Inject constructor(
    private val appDatabase: AppDatabase,
    private val stockDao: StockDao
) : StockDataSource {

    override fun getStocks(): Flow<List<Stock>> {
        return flowOf(stockDao.getAll())
    }

    override fun getOne(id: Int): Flow<Stock?> {
        return flowOf(stockDao.getOne(id))
    }

    override fun insertStock(comment: String?, amount: Int?): Flow<Int> = flow {
        val result = appDatabase.withTransaction {
            val newValue = Stock(
                id = 0,
                comment = comment,
                amount = amount.orZero(),
                createDate = LocalDateTime.now()
            )
            stockDao.insert(newValue)
        }
        emit(result.toInt()) // IDを返す
    }.flowOn(Dispatchers.IO)
}