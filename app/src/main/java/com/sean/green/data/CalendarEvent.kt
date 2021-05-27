package com.sean.green.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CalendarEvent(
    val challenge: List<Challenge>,
    val save: List<Save>,
    val use: List<Use>,
    val event: List<Event>,
    val share: List<Share>
) : Parcelable