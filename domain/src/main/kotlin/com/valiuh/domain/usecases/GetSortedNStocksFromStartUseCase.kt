package com.valiuh.domain.usecases

import com.valiuh.data.Repository
import com.valiuh.data.model.StockItemDAO
import io.reactivex.rxjava3.core.Observable

class GetSortedNStocksFromStartUseCase(private val repository: Repository) {

    operator fun invoke(lastIndex: Int): Observable<List<StockItemDAO>> {
        return repository
            .getSortedStocksByPrice()
            .flatMapIterable { list -> list.slice(0 until lastIndex) }
            .toList()
            .toObservable()
    }
}