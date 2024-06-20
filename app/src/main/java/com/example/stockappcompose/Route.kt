package com.example.stockappcompose

sealed class Route(
    val screen: Screen,
    val value: String,
) {
    class StockList: Route(screen = Screen.StockList, value = Screen.StockList.routePrefix)
    class StockDetail(stock: Stock): Route(
        screen = Screen.StockDetail,
        value = "${Screen.StockDetail.routePrefix}/${stock.amount}/?comment=${stock.comment}/${stock.createDate.format(Constants.DATETIME_FORMAT_YYYYMMDDHHMMSSSSS)}/?imageUri=${stock.imageUri}"
    )
}