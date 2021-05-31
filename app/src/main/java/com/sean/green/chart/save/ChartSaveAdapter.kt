package com.sean.green.chart.save

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sean.green.data.Save
import com.sean.green.databinding.ItemChartDateSaveBinding

class ChartSaveAdapter():ListAdapter<Save, ChartSaveAdapter.ViewHolder>(
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

        class ViewHolder(var binding: ItemChartDateSaveBinding) : RecyclerView.ViewHolder(binding.root) {

            fun bind(item: Save) {
                Log.d("seanViewHolder","fun bind(item: Save) = $item")
                binding.viewModel = item
            }

            companion object {
                fun from(parent: ViewGroup): ViewHolder {
                    val layoutInflater = LayoutInflater.from(parent.context)
                    val binding = ItemChartDateSaveBinding.inflate(layoutInflater, parent, false)
                    return ViewHolder(
                        binding
                    )
                }
            }
        }

        class DiffCallback : DiffUtil.ItemCallback<Save>() {
            override fun areItemsTheSame(oldItem: Save, newItem: Save): Boolean {
                return oldItem == newItem
            }


            override fun areContentsTheSame(oldItem: Save, newItem: Save): Boolean {
                return oldItem == newItem
            }
        }
    }
