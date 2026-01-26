package com.example.stockappcompose

import com.example.stockappcompose.data.error.base.BaseError
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.format(format: String): String {
    val dtf = DateTimeFormatter.ofPattern(format)
    return this.format(dtf)
}

fun String.toLocalDateTime(format: String): LocalDateTime {
    val dtf = DateTimeFormatter.ofPattern(format)
    return LocalDateTime.parse(this, dtf)
}

fun Int?.orZero(): Int = this ?: 0

fun <V, E> Flow<Result<V, E>>.andResult(): Flow<Result<V, BaseError>> {
    return map {
        it as Result<V, BaseError>
    }
}