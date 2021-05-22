package com.sean.green.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Save (

    var id: String ? ="",
    var plastic: String ?= "",
    var power: String ?= "",
    var carbon: String ?= "",
    var content: String ?= "",
    var createdTime: Long ?= -1,
//    val image: List<String> ?= null,
//    val totalSave: Int ?= null,
) : Parcelable