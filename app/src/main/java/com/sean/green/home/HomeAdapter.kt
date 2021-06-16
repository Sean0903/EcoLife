package com.sean.green.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sean.green.data.Article
import com.sean.green.databinding.ItemHomePictureBinding

class HomeAdapter():
    ListAdapter<Article, HomeAdapter.ViewHolder>(HomeAdapter.DiffCallback()) {

        //實體化ViewHolder然後把View與ViewHolder綁定
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            return ViewHolder.from(parent)

        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            holder.bind(getItem(position))

        }

        class ViewHolder(var binding: ItemHomePictureBinding ) : RecyclerView.ViewHolder(binding.root) {

            fun bind(item: Article) {
//            Log.d("seanViewHolder","fun bind(item: Save) = $item")
                binding.article = item
            }

            companion object {
                fun from(parent: ViewGroup): ViewHolder {
                    val layoutInflater = LayoutInflater.from(parent.context)
                    val binding = ItemHomePictureBinding.inflate(layoutInflater, parent, false)
                    return ViewHolder(binding)
                }
            }
        }

        class DiffCallback : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }


            override fun areContentsTheSame(oldItem:Article, newItem:Article): Boolean {
                return oldItem == newItem
            }
        }
}