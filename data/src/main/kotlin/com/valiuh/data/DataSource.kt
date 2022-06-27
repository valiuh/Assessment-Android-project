package com.valiuh.data

import io.reactivex.rxjava3.core.Observable

interface DataSource<T> {
    fun fetchDataItems(): Observable<List<T>>
}