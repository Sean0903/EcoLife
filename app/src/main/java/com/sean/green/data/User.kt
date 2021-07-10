package com.sean.green.data

import android.icu.util.Calendar
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var userId: String = "",
    var userName: String = "",
    var email: String = "",
    var image: String = "",
    var createdTime: Long = Calendar.getInstance().timeInMillis,
) : Parcelable