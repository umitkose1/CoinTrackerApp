package com.demo.cointrackerapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.cointrackerapp.databinding.ListHistoryItemBinding
import com.demo.cointrackerapp.databinding.ListItemBinding
import com.demo.cointrackerapp.model.CoinHistoryResponse
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

class CoinHistoryAdapter(
    private var coinHistoryList: CoinHistoryResponse
) : RecyclerView.Adapter<CoinHistoryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ListHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListHistoryItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
                        binding.tvPrice.text = coinHistoryList.prices[position][1].toString() + "$"
                        binding.tvMarketcap.text = coinHistoryList.market_caps[position][1].toString() + "B $"
                        binding.tvVolume.text = coinHistoryList.total_volumes[position][1].toString() + "B $"
        }
    }

    override fun getItemCount(): Int {
        return coinHistoryList.prices.indices.last
    }

}