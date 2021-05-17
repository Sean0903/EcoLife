package com.sean.green

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            //若網路不穩或圖片損壞，預設會顯示此圖片，避免呈現空白
//            .placeholder(R.drawable.image_placeholder)
            .into(imgView)
    }
}