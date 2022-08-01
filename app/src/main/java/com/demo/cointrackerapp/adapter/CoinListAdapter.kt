package com.demo.cointrackerapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.cointrackerapp.databinding.ListItemBinding
import com.demo.cointrackerapp.model.BaseResponse

class CoinListAdapter(
    private var coinList: BaseResponse,
    private val listener: (String) -> Unit
) : RecyclerView.Adapter<CoinListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            when(position){
                0 -> { binding.coinPrice.text = coinList.bitcoin.usd.toString() + " $"
                    binding.coinName.text = "Bitcoin" }
                1 ->  { binding.coinPrice.text = coinList.ethereum.usd.toString() + " $"
                    binding.coinName.text = "Ethereum" }
                2 ->  { binding.coinPrice.text = coinList.ripple.usd.toString() + " $"
                    binding.coinName.text = "Ripple" }

            }
        }
        holder.binding.ibFavorite.setOnClickListener {
            when(position){
                0 -> { listener("btc") }
                1 ->  { listener("eth") }
                2 ->  { listener("xrp") }
            }
        }
        holder.itemView.setOnClickListener {
            when(position){
                0 -> { listener("bitcoin") }
                1 ->  { listener("ethereum") }
                2 ->  { listener("ripple") }
            }
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

}