package com.sean.green.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(
    var id: String? = "",
    val content: String? = "",
    var image: String = "",
    var createdTime: Long = -1,
    var challenge: String? = "",
    var save: String? = "",
    var use: String? = "",
) : Parcelable