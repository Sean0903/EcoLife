package com.sean.green.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Save (
    val plastic: String ?= "",
    val power: String ?= "",
    val carbon: String ?= "",
    val content: String ?= "",
    val createdTime: Long = -1,
//    val image: List<String> ?= null,
//    val totalSave: Int ?= null,
) : Parcelable