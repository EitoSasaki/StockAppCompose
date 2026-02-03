package com.example.stockappcompose.data.error.base

import com.example.stockappcompose.enumeration.ui.MessageType

abstract class BaseError(
    open var messageType: MessageType? = null,
) {
    var cause: String? = null
    var exception: Exception? = null

    fun from(t: Throwable?): BaseError {
        cause = t?.message
        exception = t?.let { Exception(it) }
        return this
    }
}