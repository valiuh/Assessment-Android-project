package com.valiuh.domain.usecases

import com.valiuh.data.Repository
import com.valiuh.data.model.StockItemDAO
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.TestObserver
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test


class GetSortedNStocksFromStartUseCaseTest {

    private val items: List<StockItemDAO> = arrayListOf(
        mockk<StockItemDAO>(),
        mockk<StockItemDAO>(),
        mockk<StockItemDAO>(),
        mockk<StockItemDAO>(),
        mockk<StockItemDAO>(),
        mockk<StockItemDAO>(),
        mockk<StockItemDAO>(),
        mockk<StockItemDAO>(),
        mockk<StockItemDAO>(),
        mockk<StockItemDAO>()
    )

    private val mockRepository = mockk<Repository>()
    private var useCase = GetSortedNStocksFromStartUseCase(mockRepository)

    private var testObserver = TestObserver<List<StockItemDAO>>()

    init {
        every { mockRepository.getSortedStocksByPrice() } returns Observable.fromArray(items)
    }

    @Test
    fun `test that Use Case return specific number of elements`() {
        // WHEN
        useCase.invoke(5).subscribe(testObserver)

        // THEN
        testObserver.assertComplete();
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        val listResult = testObserver.values()[0]

        assertThat(listResult.size, `is`(5))

    }
}

