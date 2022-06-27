package com.valiuh.data

import com.valiuh.data.model.StockItemDAO
import io.reactivex.rxjava3.core.Observable


interface Repository {

    fun getAllStockItems(): Observable<List<StockItemDAO>>

    fun getSortedStocksByPrice(): Observable<List<StockItemDAO>>

}