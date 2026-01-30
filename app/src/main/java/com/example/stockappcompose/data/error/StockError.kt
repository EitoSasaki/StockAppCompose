package com.example.stockappcompose.data.error

import android.os.Message
import com.example.stockappcompose.data.error.base.BaseError
import com.example.stockappcompose.data.ui.MessageType

sealed class StockError(
    override var messageType: MessageType? = null,
) : BaseError() {

    class StockGetError : StockError(messageType = MessageType.StockGetError)
    class StockInsertError : StockError(messageType = MessageType.StockInsertError)
    class StockUpdateError : StockError(messageType = MessageType.StockUpdateError)
}