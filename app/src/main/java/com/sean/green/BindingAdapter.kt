package com.sean.green

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sean.green.data.Event
import com.sean.green.data.Share
import com.sean.green.event.EventAdapter
import com.sean.green.event.eventImage.EventImageAdapter
import com.sean.green.ext.toDisplayFormat
import com.sean.green.share.ShareAdapter

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    Log.d("seanImageUrl","image = $imgUrl ")
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            //若網路不穩或圖片損壞，預設會顯示此圖片，避免呈現空白
//            .placeholder(R.drawable.image_placeholder)
            .into(imgView)
    }
}

@BindingAdapter("glide")
fun glidingImage(imageView: ImageView, url: String?) {
    url?.let {
        val uri = it.toUri().buildUpon().build()

        Glide.with(imageView.context)
            .load(uri).apply(
                RequestOptions()
//                    .placeholder(R.drawable._01_arm_wrestling)
//                    .error(R.drawable._01_arm_wrestling)
            )
            .into(imageView)
    }
}

@BindingAdapter("timeToDisplayFormat")
fun bindDisplayFormatTime(textView: TextView, time: Long?) {
    textView.text = time?.toDisplayFormat()
}

@BindingAdapter("event")
fun bindRecyclerViewWithEvent(recyclerView: RecyclerView, event: List<Event>?) {
    event?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is EventAdapter -> {
                    notifyDataSetChanged()
                    submitList(it)
                }
            }
        }
    }
}

@BindingAdapter("listEventImage")
fun bindRecycleViewWithEventImage(recyclerView: RecyclerView, data : List<String>?) {
    Log.d("checkImage", "child data = $data")

    data?.let {
        recyclerView.adapter.apply {
            when (this) {
                is EventImageAdapter -> submitList(it)
            }
            Log.d("checkImage4", "this = $this")
            Log.d("checkImage2", "child data2 = $it")
        }
        Log.d("checkImage3", "child data3 = $it")
    }
}

@BindingAdapter("share")
fun bindRecyclerViewWithShare(recyclerView: RecyclerView, share: List<Share>?) {
    share?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is ShareAdapter -> {
                    notifyDataSetChanged()
                    submitList(it)
                }
            }
        }
    }
}