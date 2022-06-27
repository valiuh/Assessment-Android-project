package com.valiuh.data.repository

import com.valiuh.data.DataSource
import com.valiuh.data.model.StockItemDAO
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.TestObserver
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test


class SimpleLocalStockRepositoryTest {

    private val items: List<StockItemDAO> = arrayListOf(
        StockItemDAO(
            id = 1,
            name = "MSFT",
            price = 267.70),
        StockItemDAO(
            id = 2,
            name = "AAPL",
            price = 141.66),
        StockItemDAO(
            id = 3,
            name = "GOOG",
            price = 2370.76)
    )

    private val mockDataSource = mockk<DataSource<StockItemDAO>>()
    private val repository = SimpleLocalStockRepository(mockDataSource)

    private var testObserver = TestObserver<List<StockItemDAO>>()

    init {
        every { mockDataSource.fetchDataItems() } returns Observable.fromArray(items)
    }

    @Test
    fun `test that Repository does not distort input data`() {
        // WHEN
        repository.getAllStockItems().subscribe(testObserver)

        // THEN
        testObserver.assertComplete();
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        val listResult = testObserver.values()[0]

        assertThat(listResult.size, `is`(3))

        assertThat(listResult[0].id, `is`(1))
        assertThat(listResult[0].name, `is`("MSFT"))
        assertThat(listResult[0].price, `is`(267.70))

        assertThat(listResult[1].id, `is`(2))
        assertThat(listResult[1].name, `is`("AAPL"))
        assertThat(listResult[1].price, `is`(141.66))


        assertThat(listResult[2].id, `is`(3))
        assertThat(listResult[2].name, `is`("GOOG"))
        assertThat(listResult[2].price, `is`(2370.76))
    }

    @Test
    fun `test that Repository can sort stocks by price`() {
        // WHEN
        repository.getSortedStocksByPrice().subscribe(testObserver)

        // THEN
        val listResult = testObserver.values()[0]

        assertThat(listResult.size, `is`(3))

        assertThat(listResult[1].id, `is`(1))
        assertThat(listResult[1].name, `is`("MSFT"))
        assertThat(listResult[1].price, `is`(267.70))

        assertThat(listResult[2].id, `is`(2))
        assertThat(listResult[2].name, `is`("AAPL"))
        assertThat(listResult[2].price, `is`(141.66))


        assertThat(listResult[0].id, `is`(3))
        assertThat(listResult[0].name, `is`("GOOG"))
        assertThat(listResult[0].price, `is`(2370.76))
    }
}

