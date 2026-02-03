package com.example.stockappcompose.data.ui

import com.example.stockappcompose.enumeration.ui.Screen

sealed class Route(
    val screen: Screen,
    val value: String,
) {
    class StockList: Route(screen = Screen.StockList, value = Screen.StockList.routePrefix)
    class StockDetail(id: Int): Route(
        screen = Screen.StockDetail,
        value = "${Screen.StockDetail.routePrefix}/${id}"
    )
}