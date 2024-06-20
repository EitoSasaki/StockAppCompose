package com.example.stockappcompose

import java.time.LocalDateTime

data class Stock(
    val comment: String?,
    val amount: Int,
    val createDate: LocalDateTime,
    var imageUri: String? = null
)
