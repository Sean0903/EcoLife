package com.sean.green.data

import android.os.Parcelable

data class User (
    val user_email: String ?= null,
    val user_name: String ?= null,
    val user_picture: String ?= null,
    val Save: List<Save> ?=null,
    val Use: List<Use> ?=null,
    val Challenge: List<Challenge> ?= null
)