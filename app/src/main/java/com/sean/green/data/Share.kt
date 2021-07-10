package com.sean.green.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Share(
    var id: String? = "",
    var name: String = "",
    val achievement: String? = "",
    val time: String? = "",
    val content: String? = "",
    var today: String? = "",
    var createdTime: Long = -1,
    var image: String = "",
    var email: String = "",
) : Parcelable