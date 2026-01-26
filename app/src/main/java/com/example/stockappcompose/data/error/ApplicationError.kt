package com.example.stockappcompose.data.error

import com.example.stockappcompose.data.error.base.BaseError

sealed class ApplicationError(
    override var message: String? = null,
) : BaseError() {

    class DatabaseError: ApplicationError(message = "データベースエラー")
    class UnknownError : ApplicationError(message = "予期せぬエラー")
}