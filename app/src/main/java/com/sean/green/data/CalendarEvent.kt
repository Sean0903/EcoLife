package com.sean.green.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CalendarEvent(
    val challenge: String? = null,
    val save: String? = null,
    val use: String? = null,
    val event: String? = null,
    val share: String? = null,
    val createdTime: Long = -1,
    val day: String = "",
    val month: String = "",
    val year: String = ""
) : Parcelable