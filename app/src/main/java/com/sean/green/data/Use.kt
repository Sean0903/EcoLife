package com.sean.green.data

data class Use(

    var id: String? = "",
    val use_plastic: Int? = null,
    val use_power: Int? = null,
    val use_carbon: Int? = null,
    var createdTime: Long = -1,
    //    val totalUse: Int ?= null,
//    val use_content: String ?= null,
)