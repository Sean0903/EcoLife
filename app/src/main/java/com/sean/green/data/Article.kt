package com.sean.green.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(

    var id: String? = "",
    val content: String? = "",
    var createdTime: Long = -1,
    var hourAndMin: String = ""

    ): Parcelable