package com.example.stockappcompose.data.ui

import androidx.annotation.StringRes
import com.example.stockappcompose.R

enum class MessageType(
    @StringRes val messageId: Int,
    val dialogType: DialogType,
    val code: Int,
) {
    // Info
    CheckedSum(
        messageId = R.string.message_sum,
        dialogType = DialogType.Info,
        code = 0,
    ),

    // Warning

    // Error
    StockGetError(
        messageId = R.string.message_stock_get_error,
        dialogType = DialogType.Error,
        code = 5001,
    ),

    StockInsertError(
        messageId = R.string.message_stock_get_error,
        dialogType = DialogType.Error,
        code = 5002,
    ),

    StockUpdateError(
        messageId = R.string.message_stock_get_error,
        dialogType = DialogType.Error,
        code = 5003,
    ),

    UnknownError(
        messageId = R.string.message_stock_get_error,
        dialogType = DialogType.Error,
        code = 9999,
    ),
}