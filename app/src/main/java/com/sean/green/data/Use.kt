package com.sean.green.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Use(
    var id: String? = "",
    val plastic: Int? = null,
    val power: Int? = null,
    val carbon: Int? = null,
    var today: String? = "",
    var createdTime: Long = -1,
) : Parcelable