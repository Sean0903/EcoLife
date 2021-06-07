package com.sean.green

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sean.green.ext.toDisplayFormat

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