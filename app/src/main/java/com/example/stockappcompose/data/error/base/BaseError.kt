package com.example.stockappcompose.data.error.base

abstract class BaseError constructor(
    open var message: String? = null,
) {
    var cause: String? = null
    var exception: Exception? = null

    fun from(t: Throwable?): BaseError {
        cause = t?.message
        exception = t?.let { Exception(it) }
        return this
    }
}