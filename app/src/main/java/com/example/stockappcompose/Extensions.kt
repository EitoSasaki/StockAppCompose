package com.example.stockappcompose

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