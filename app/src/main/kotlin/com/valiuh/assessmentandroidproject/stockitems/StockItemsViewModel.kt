package com.valiuh.assessmentandroidproject.stockitems

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.valiuh.assessmentandroidproject.di.DependencyProvider
import com.valiuh.assessmentandroidproject.model.StockItem
import com.valiuh.data.model.StockItemDAO
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class StockItemsViewModel : ViewModel() {
    private val useCase = DependencyProvider.getSortedNStocksFromStartUseCase

    private val stockItemsOldPrices = mutableMapOf<Int, Double>()

    private val _currentStockItems = MutableLiveData<List<StockItem>>()
    val currentStockItems: LiveData<List<StockItem>>
        get() = _currentStockItems

    private lateinit var disposable: Disposable;

    init {
        fetchUpdates()
    }

    fun fetchUpdates() {
        disposable = doOnFetch()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ stocks -> _currentStockItems.postValue(stocks)}
    }

    fun onDestroy(){
        disposable.dispose()
    }

    private fun doOnFetch(): Observable<List<StockItem>>{
        return useCase
            .invoke(5)
            .flatMapIterable { list: List<StockItemDAO> -> list }
            .map { dao -> toStockItem(dao).also { stockItemsOldPrices[dao.id] = dao.price } }
            .toList()
            .toObservable()
    }

    private fun toStockItem(dao: StockItemDAO): StockItem{
        val oldPrice = stockItemsOldPrices[dao.id]
        if(oldPrice != null) {
            val change: Double = oldPrice - dao.price
            return if(change < 0) {
                StockItem(
                    name = dao.name,
                    price = dao.price.format(2),
                    priceUpVisibility = View.VISIBLE,
                    priceDownVisibility = View.GONE)
            } else if (change > 0){
                StockItem(
                    name = dao.name,
                    price = dao.price.format(2),
                    priceUpVisibility = View.GONE,
                    priceDownVisibility = View.VISIBLE)
            } else {
                StockItem(
                    name = dao.name,
                    price = dao.price.format(2),
                    priceUpVisibility = View.GONE,
                    priceDownVisibility = View.GONE)
            }
        } else {
            return StockItem(
                name = dao.name,
                price = dao.price.format(2),
                priceUpVisibility = View.GONE,
                priceDownVisibility = View.GONE)
        }
    }

    fun Double.format(digits: Int) = "%.${digits}f $".format(this)
}