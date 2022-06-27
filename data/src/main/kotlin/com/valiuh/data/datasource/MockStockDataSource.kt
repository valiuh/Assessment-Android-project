package com.valiuh.data.datasource

import com.valiuh.data.DataSource
import com.valiuh.data.model.StockItemDAO
import io.reactivex.rxjava3.core.Observable
import kotlin.random.Random

class MockStockDataSource : DataSource<StockItemDAO> {
    override fun fetchDataItems(): Observable<List<StockItemDAO>> {
        val items: List<StockItemDAO> = arrayListOf(
            StockItemDAO(
                id = 1,
                name = "AAPL",
                price = 141.66 + getRandomChange()),
            StockItemDAO(
                id = 2,
                name = "MSFT",
                price = 267.70 + getRandomChange()),
            StockItemDAO(
                id = 3,
                name = "GOOG",
                price = 2370.76 + getRandomChange()),
            StockItemDAO(
                id = 4,
                name = "ABCD",
                price = 2145.50 + getRandomChange()),
            StockItemDAO(
                id = 5,
                name = "AMZN",
                price = 116.46 + getRandomChange()),
            StockItemDAO(
                id = 6,
                name = "TSLA",
                price = 737.12 + getRandomChange()),
            StockItemDAO(
                id = 7,
                name = "SHOP",
                price = 182.29 + getRandomChange()),
            StockItemDAO(
                id = 8,
                name = "JNJ",
                price = 170.16 + getRandomChange()),
            StockItemDAO(
                id = 9,
                name = "UNH",
                price = 85.92 + getRandomChange()),
            StockItemDAO(
                id = 10,
                name = "META",
                price = 86.90 + getRandomChange()),
        )
        return Observable.fromArray(items)
    }

    private fun getRandomChange(): Double = Random.nextDouble(from = -5.0, until = 5.0)
}