package com.sean.green.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CalendarEvent(
    val challenge: String? = "",
    val save: String? = "",
    val use: String? = "",
    val event: String? = "",
    val share: String? = "",
    val createdTime: Long = -1,
    val day: String = "",
    val month: String = "",
    val year: String = ""
) : Parcelable