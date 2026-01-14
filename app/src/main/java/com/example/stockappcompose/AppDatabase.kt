package com.example.stockappcompose

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.stockappcompose.data.db.Stock
import com.example.stockappcompose.data.db.common.Converter
import com.example.stockappcompose.data.db.dao.StockDao

@Database(entities = [Stock::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stockDao(): StockDao
}
