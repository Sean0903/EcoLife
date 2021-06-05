package com.sean.green.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(

    var userId: String = "",
    var userName: String = "",
    var userImage: String = "",
    var createdTime: Long = -1,
    val Save: List<Save>? = null,
    val Use: List<Use>? = null,
    val Challenge: List<Challenge>? = null
) : Parcelable