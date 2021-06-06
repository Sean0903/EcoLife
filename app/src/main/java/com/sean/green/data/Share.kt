package com.sean.green.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Share(

    var id: String? = "",
    val name: String? = "",
    val achievement: String? = "",
    val time: String? = "",
    val content: String? = "",
    val dialog: String? = "",
    var today: String? = "",
    var createdTime: Long = -1,

) : Parcelable