package com.example.stockappcompose.data.ui

import androidx.annotation.StringRes
import com.example.stockappcompose.R

enum class MessageType(
    @StringRes val messageId: Int,
    val dialogType: DialogType,
    val code: Int,
) {
    CheckedSum(
        messageId = R.string.message_sum,
        dialogType = DialogType.Info,
        code = 0,
    )
}