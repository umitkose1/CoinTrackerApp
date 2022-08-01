package com.demo.cointrackerapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.cointrackerapp.data.local.Alerts
import com.demo.cointrackerapp.databinding.ListItemAlertsBinding

class CoinAlertsAdapter(
    private var alertsList: List<Alerts>,
    private val longClickListener: (Alerts) -> Unit
) : RecyclerView.Adapter<CoinAlertsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ListItemAlertsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemAlertsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {

            with(alertsList[position]) {
                binding.tvName.text = itemName
                binding.tvMinValue.text = "$itemMinValue $"
                binding.tvMaxValue.text = "$itemMaxValue $"
            }
        }
        holder.itemView.setOnLongClickListener {
            longClickListener(alertsList[position])
            return@setOnLongClickListener false
        }
    }

    override fun getItemCount(): Int {
        return alertsList.size
    }

}