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
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDateTime
import javax.inject.Inject

class StockLocalDataSource @Inject constructor(
    private val appDatabase: AppDatabase,
    private val stockDao: StockDao
) : StockDataSource {

    override fun getStocks(): Flow<List<Stock>> = flow {
        val result = stockDao.getAll()
        emit(result)
    }.flowOn(Dispatchers.IO)

    override fun getOne(id: Int): Flow<Stock?> = flow {
        val result = stockDao.getOne(id)
        emit(result)
    }.flowOn(Dispatchers.IO)

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

    override fun updateStock(stock: Stock): Flow<Unit> = flow {
        val result = appDatabase.withTransaction {
            stockDao.update(stock)
        }
        emit(result)
    }.flowOn(Dispatchers.IO)

    override fun deleteStock(stock: Stock): Flow<Unit> = flow {
        val result = appDatabase.withTransaction {
            stockDao.delete(stock)
        }
        emit(result)
    }.flowOn(Dispatchers.IO)
}