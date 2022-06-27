package com.valiuh.data.repository

import com.valiuh.data.DataSource
import com.valiuh.data.Repository
import com.valiuh.data.model.StockItemDAO
import io.reactivex.rxjava3.core.Observable
import kotlin.math.abs

class SimpleLocalStockRepository(private val dataSource: DataSource<StockItemDAO>): Repository {

    override fun getAllStockItems(): Observable<List<StockItemDAO>> = dataSource.fetchDataItems()

    override fun getSortedStocksByPrice(): Observable<List<StockItemDAO>> {
        return getAllStockItems()
            .map{it ->
                it.sortedByDescending { abs(it.price) }}
    }
}
