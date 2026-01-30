package com.example.stockappcompose.extension

import com.example.stockappcompose.data.error.base.BaseError
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
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

@OptIn(ExperimentalCoroutinesApi::class)
fun <V, U> Flow<Result<V, BaseError>>.flatMapSuccess(success: suspend (V) -> Flow<Result<U, BaseError>>): Flow<Result<U, BaseError>> {
    return flatMapConcat {
        when (it) {
            is Ok -> { success(it.value) }
            else -> { flowOf(it) }
        } as Flow<Result<U, BaseError>>
    }
}