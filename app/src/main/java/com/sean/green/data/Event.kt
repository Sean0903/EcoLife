package com.sean.green.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(
    var id: String = "",
    var hostName: String = "",
    var hostImage: String = "",
    var hostEmail: String = "",
    val introduction: String? = "",
    val location: String? = "",
    val content: String? = "",
    var createdTime: Long = -1,
    val eventYMD: String = "",
    var eventYear: String? = "",
    var eventMonth: String? = "",
    var eventDay: String? = "",
    var eventTimestamp: Long = -1,
    val members: List<String> = listOf(""),
    val memberImages: List<String> = listOf(""),
) : Parcelable