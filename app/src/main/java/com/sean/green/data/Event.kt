package com.sean.green.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(

    var id: String = "",
    val name: String? = "",
    val introduction: String? = "",
    val time:String? = "",
    val location: String? = "",
    val content: String? = "",
    val member: List<String> = listOf(""),
    val memberImage: List<String> = listOf(""),
    var today: String? = "",
    var createdTime: Long = -1,
    var image: String = "",
    var userName: String = "",
    var email: String = "",

): Parcelable