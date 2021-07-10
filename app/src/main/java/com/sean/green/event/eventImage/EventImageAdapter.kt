package com.sean.green.event.eventImage

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sean.green.databinding.ItemEventMemberImageBinding

class EventImageAdapter() : ListAdapter<String, EventImageAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder.from(parent)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(getItem(position))

    }

    class ViewHolder(var binding: ItemEventMemberImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            Log.d("eventImageAdapter", "eventMemberImage = $item")
            binding.memberImage = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemEventMemberImageBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(
                    binding
                )
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }


        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}