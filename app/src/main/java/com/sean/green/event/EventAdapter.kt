package com.sean.green.event

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sean.green.data.Event
import com.sean.green.databinding.ItemEventBinding
import com.sean.green.event.eventImage.EventImageAdapter
import com.sean.green.login.UserManager
import kotlinx.android.synthetic.main.item_event.view.*

class EventAdapter(val viewModel: EventViewModel, val onClickListener: OnClickListener) :
    ListAdapter<Event, EventAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder.from(parent)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(getItem(position), viewModel)


        holder.itemView.setOnClickListener {
            onClickListener.onClick(event)
        }

        holder.itemView.button_item_event_attend.setOnClickListener {
            viewModel.addMemberToEvent(event, UserManager.user.email, UserManager.user.image)
            viewModel.addEventInfo2UserFirebase(event,UserManager.user.email)
        }

    }

    class ViewHolder(var binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Event, viewModel: EventViewModel) {
            Log.d("eventAdapter", "fun bind(item: Save) = $item")
            binding.eventData = item
//            binding.viewModel = viewModel

            // Nested RecyclerView - Child Adapter
            val eventImageAdapter = EventImageAdapter()
            itemView.recyclerView_event_image.adapter = eventImageAdapter

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemEventBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }


        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }
    }

    class OnClickListener(val clickListener: (event: Event) -> Unit) {
        fun onClick(event: Event) = clickListener(event)
    }
}