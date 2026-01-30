package com.example.stockappcompose.datasource

import androidx.room.withTransaction
import com.example.stockappcompose.domain.AppDatabase
import com.example.stockappcompose.extension.andResult
import com.example.stockappcompose.data.db.Stock
import com.example.stockappcompose.data.db.dao.StockDao
import com.example.stockappcompose.data.error.StockError
import com.example.stockappcompose.data.error.base.BaseError
import com.example.stockappcompose.datasource.`interface`.StockDataSource
import com.example.stockappcompose.extension.orZero
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDateTime
import javax.inject.Inject

class StockLocalDataSource @Inject constructor(
    private val appDatabase: AppDatabase,
    private val stockDao: StockDao
) : StockDataSource {

    override fun getStocks(): Flow<Result<List<Stock>, BaseError>> = flow {
        val result = stockDao.getAll()
        emit(Ok(result))
    }.flowOn(Dispatchers.IO).andResult().catch { t ->
        emit(Err(StockError.StockGetError().from(t)))
    }

    override fun getOne(id: Int): Flow<Result<Stock?, BaseError>> = flow {
        val result = stockDao.getOne(id)
        emit(Ok(result))
    }.flowOn(Dispatchers.IO).andResult().catch { t ->
        emit(Err(StockError.StockGetError().from(t)))
    }

    override fun insertStock(comment: String?, amount: Int?): Flow<Result<Int, BaseError>> = flow {
        val result = appDatabase.withTransaction {
            val newValue = Stock(
                id = 0,
                comment = comment,
                amount = amount.orZero(),
                createDate = LocalDateTime.now()
            )
            stockDao.insert(newValue)
        }
        emit(Ok(result.toInt())) // IDを返す
    }.flowOn(Dispatchers.IO).andResult().catch { t ->
        emit(Err(StockError.StockInsertError().from(t)))
    }

    override fun updateStock(stock: Stock): Flow<Result<Unit, BaseError>> = flow {
        val result = appDatabase.withTransaction {
            stockDao.update(stock)
        }
        emit(Ok(result))
    }.flowOn(Dispatchers.IO).andResult().catch { t ->
        emit(Err(StockError.StockUpdateError().from(t)))
    }

    override fun deleteStock(stock: Stock): Flow<Result<Unit, BaseError>> = flow {
        val result = appDatabase.withTransaction {
            stockDao.delete(stock)
        }
        emit(Ok(result))
    }.flowOn(Dispatchers.IO).andResult().catch { t ->
        emit(Err(StockError.StockUpdateError().from(t)))
    }

    override fun deleteAll(): Flow<Result<Unit, BaseError>> = flow {
        val result = appDatabase.withTransaction {
            stockDao.deleteAll()
        }
        emit(Ok(result))
    }.flowOn(Dispatchers.IO).andResult().catch { t ->
        emit(Err(StockError.StockUpdateError().from(t)))
    }
}