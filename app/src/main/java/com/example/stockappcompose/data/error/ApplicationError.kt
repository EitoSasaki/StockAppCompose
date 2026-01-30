package com.example.stockappcompose.data.error

import com.example.stockappcompose.data.error.base.BaseError
import com.example.stockappcompose.data.ui.MessageType

sealed class ApplicationError(
    override var messageType: MessageType? = null,
) : BaseError() {
    class UnknownError : ApplicationError(messageType = MessageType.UnknownError)
}