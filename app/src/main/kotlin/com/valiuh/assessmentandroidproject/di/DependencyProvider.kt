package com.valiuh.assessmentandroidproject.di

import com.valiuh.data.Repository
import com.valiuh.data.repository.SimpleLocalStockRepository
import com.valiuh.data.datasource.MockStockDataSource
import com.valiuh.domain.usecases.GetSortedNStocksFromStartUseCase
import com.valiuh.domain.usecases.GetSortedStocksInRangeUseCase

object DependencyProvider {

    private val repository: Repository
        get() = SimpleLocalStockRepository(MockStockDataSource())

    val getSortedNStocksFromStartUseCase: GetSortedNStocksFromStartUseCase
        get() = GetSortedNStocksFromStartUseCase(repository)

    val getSortedStocksInARangeUseCase: GetSortedStocksInRangeUseCase
        get() = GetSortedStocksInRangeUseCase(repository)
}