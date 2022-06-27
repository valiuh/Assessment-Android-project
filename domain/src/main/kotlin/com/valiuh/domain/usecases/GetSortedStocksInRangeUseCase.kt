package com.valiuh.domain.usecases

import com.valiuh.data.Repository
import com.valiuh.data.model.StockItemDAO
import io.reactivex.rxjava3.core.Observable

class GetSortedStocksInRangeUseCase(private val repository: Repository) {

    operator fun invoke(start: Int, end: Int): Observable<List<StockItemDAO>> {
        return repository
            .getSortedStocksByPrice()
            .flatMapIterable { list -> list.slice(start until end) }
            .toList()
            .toObservable()
    }

}