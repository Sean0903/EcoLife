package com.sean.green.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Chart(
    var day: String = "",
    var plastic: List<Int>? = listOf(),
    var power: List<Int>? = listOf(),
    var carbon: List<Int>? = listOf()
) : Parcelable