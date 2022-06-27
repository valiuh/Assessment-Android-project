package com.valiuh.assessmentandroidproject.stockitems

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.valiuh.assessmentandroidproject.databinding.FragmentStockItemsBinding
import com.valiuh.assessmentandroidproject.model.StockItem

class StockItemsFragment : Fragment() {

    private lateinit var binding:  FragmentStockItemsBinding
    private lateinit var viewModel: StockItemsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StockItemsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        binding = FragmentStockItemsBinding.inflate(inflater, container, false)
        binding.swipeContainer.setOnRefreshListener { viewModel.fetchUpdates() }

        viewModel.currentStockItems.observe(viewLifecycleOwner, this::onCurrentStocksChanged)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    private fun onCurrentStocksChanged(stocks: List<StockItem>) {
        binding.list.adapter = StockItemsRecyclerViewAdapter(stocks)
        binding.swipeContainer.isRefreshing = false
    }
}