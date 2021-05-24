package com.sean.green.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Save(

    var id: String? = "",
    var plastic: Int? = null,
    var power: Int? = null,
    var carbon: Int? = null,
    var content: String? = "",
    var createdTime: Long = -1,
//    val image: List<String> ?= null,
//    val totalSave: Int ?= null,
) : Parcelable