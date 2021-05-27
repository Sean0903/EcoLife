package com.sean.green.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(
    val name: String? = null,
    val introduction: String? = null,
    val time: String? = null,
    val location: String? = null,
    val content: String? = null,
    val member: String? = null
): Parcelable