package com.example.stockappcompose.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.stockappcompose.data.db.Stock

@Dao
interface StockDao {
    @Query("SELECT * FROM stock")
    fun getAll(): List<Stock>

    @Query("SELECT * FROM stock WHERE id IN (:id)")
    fun getOne(id: Int): Stock?

    @Insert
    fun insert(stock: Stock): Long

    @Update
    fun update(stock: Stock)

    @Delete
    fun delete(stock: Stock)

    @Query("DELETE FROM stock")
    fun deleteAll()
}