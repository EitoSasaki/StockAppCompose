package com.example.stockappcompose.data.db.common

import androidx.room.TypeConverter
import com.example.stockappcompose.data.common.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class Converter {
    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? {
        return value?.let {
            val sdf = SimpleDateFormat(DateFormat.yyyyMMddHHmmssSSS.format)
            val formatDate = sdf.parse(it)
            return formatDate?.let {
                LocalDateTime.ofInstant(formatDate.toInstant(), ZoneId.systemDefault())
            }
        }
    }

    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): String? {
        val dateTimeFormatter = DateTimeFormatter.ofPattern(DateFormat.yyyyMMddHHmmssSSS.format)
        return value?.let {
            value.format(dateTimeFormatter)
        }
    }
}