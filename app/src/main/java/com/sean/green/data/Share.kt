package com.sean.green.data

import android.icu.util.Calendar
import android.os.Parcelable
import android.os.UserManager
import android.widget.ImageView
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Share(

    var id: String? = "",
    val name: String? = "",
    val achievement: String? = "",
    val time: String? = "",
    val content: String? = "",
//    val dialog: String? = "",
    var today: String? = "",
    var createdTime: Long = -1,
    var image: String = "",
    var userName: String = "",
    var email: String = "",


    ) : Parcelable