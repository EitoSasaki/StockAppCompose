package com.example.stockappcompose.data.error

import com.example.stockappcompose.data.error.base.BaseError

sealed class StockError(
    override var message: String? = null,
) : BaseError() {

    class StockGetError : StockError(message = "データ取得時のエラー")
    class StockInsertError : StockError(message = "データ追加時のエラー")
    class StockUpdateError : StockError(message = "データ更新時のエラー")
}