package com.sean.green.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CalendarEvent(
    val challenge: List<Challenge> = listOf(),
    val save: List<Save> = listOf(),
    val use: List<Use> = listOf(),
    val event: List<Event> = listOf(),
    val share: List<Share> = listOf(),
    val createdTime: Long = -1,
    val day: String = "",
    val month: String = "",
    val year: String = ""
) : Parcelable