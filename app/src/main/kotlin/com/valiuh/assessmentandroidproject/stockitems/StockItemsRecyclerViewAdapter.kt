package com.valiuh.assessmentandroidproject.stockitems

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.valiuh.assessmentandroidproject.databinding.StockItemViewBinding
import com.valiuh.assessmentandroidproject.model.StockItem

class StockItemsRecyclerViewAdapter(
    private val values: List<StockItem>
) : RecyclerView.Adapter<StockItemsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            StockItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.name.text = item.name
        holder.price.text = item.price
        holder.priceUp.visibility = item.priceUpVisibility
        holder.priceDown.visibility = item.priceDownVisibility
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: StockItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name: TextView = binding.stockName
        val price: TextView = binding.stockPrice
        val priceUp: ImageView = binding.stockPriceUp
        val priceDown: ImageView = binding.stockPriceDown

        override fun toString(): String {
            return super.toString() + " '" + name.text + "'"
        }
    }

}