package com.sean.green.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Share (
    val name: String ?= null,
    val achievement: String ?= null,
    val time: String ?= null,
    val content: String ?= null,
    val dialog: String ?= null

) : Parcelable