package com.example.stockappcompose

enum class Screen(val screenName: String, val routePrefix: String) {
    StockList("一覧画面", "StockList"),
    StockDetail("詳細画面", "StockDetail");

    companion object {
        fun fromRoute(route: String?): Screen {
            val routePrefix = route?.split('/')?.firstOrNull()
            return Screen.entries.firstOrNull { it.routePrefix == routePrefix } ?: StockList
        }
    }
}