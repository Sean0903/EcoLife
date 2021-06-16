package com.sean.green.share.message

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sean.green.data.Share
import com.sean.green.databinding.ItemShareDialogBinding

//class ShareDialogAdapter (): ListAdapter<Share, ShareDialogAdapter.ViewHolder>(DiffCallback()) {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//
//        return ViewHolder.from(parent)
//
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//
//        holder.bind(getItem(position))
//
//    }
//
//    class ViewHolder(var binding: ItemShareDialogBinding) : RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(item: Share) {
//            Log.d("seanViewHolder","fun bind(item: Save) = $item")
//            binding.viewModel = item
//        }
//
//        companion object {
//            fun from(parent: ViewGroup): ViewHolder {
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val binding = ItemShareDialogBinding.inflate(layoutInflater, parent, false)
//                return ViewHolder(binding)
//            }
//        }
//    }
//
//    class DiffCallback : DiffUtil.ItemCallback<Share>() {
//        override fun areItemsTheSame(oldItem: Share, newItem: Share): Boolean {
//            return oldItem == newItem
//        }
//
//
//        override fun areContentsTheSame(oldItem: Share, newItem: Share): Boolean {
//            return oldItem == newItem
//        }
//    }
//}