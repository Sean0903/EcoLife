package com.sean.green.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(

    var id: String? = "",
    val name: String? = "",
    val introduction: String? = "",
    val time:Long? = -1,
    val location: String? = "",
    val content: String? = "",
    val member: List<String?> = listOf(String()),
    val memberImage: List<String?> = listOf(String()),
    var today: String? = "",
    var createdTime: Long = -1,
    var image: String = "",
    var userName: String = "",
    var email: String = "",

): Parcelable