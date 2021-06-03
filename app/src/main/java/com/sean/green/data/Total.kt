package com.sean.green.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Total(
    var totalPlastic: Int? = null,
    var totalPower: Int? = null,
    var totalCarbon: Int? = null

): Parcelable