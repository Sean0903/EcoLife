package com.sean.green.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Save (
    val plastic: String ?= null,
    val power: String ?= null,
    val carbon: String ?= null,
    val content: String ?= null,
//    val image: List<String> ?= null,
//    val totalSave: Int ?= null
) : Parcelable