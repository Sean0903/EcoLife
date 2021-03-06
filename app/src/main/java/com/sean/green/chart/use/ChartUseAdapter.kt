package com.sean.green.chart.use

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sean.green.data.Use
import com.sean.green.databinding.ItemChartDateUseBinding

class ChartUseAdapter() : ListAdapter<Use, ChartUseAdapter.ViewHolder>(
    DiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder.from(
            parent
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(getItem(position))

    }

    class ViewHolder(var binding: ItemChartDateUseBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Use) {
            Log.d("chartUseAdapter", "viewHolder = $item")
            binding.useChartData = item
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemChartDateUseBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(
                    binding
                )
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Use>() {
        override fun areItemsTheSame(oldItem: Use, newItem: Use): Boolean {
            return oldItem == newItem
        }


        override fun areContentsTheSame(oldItem: Use, newItem: Use): Boolean {
            return oldItem == newItem
        }
    }
}
