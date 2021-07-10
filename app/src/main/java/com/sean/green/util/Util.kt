package com.sean.green.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.sean.green.GreenApplication
import com.sean.green.ext.toDisplayFormat
import com.sean.green.ext.toDisplayFormatDay
import com.sean.green.ext.toDisplayFormatMonth
import com.sean.green.ext.toDisplayFormatYear
import java.util.*

object Util {

    fun isInternetConnected(): Boolean {
        val cm = GreenApplication.instance
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    fun getString(resourceId: Int): String {
        return GreenApplication.instance.getString(resourceId)
    }

    fun getColor(resourceId: Int): Int {
        return GreenApplication.instance.getColor(resourceId)
    }

    val today = Calendar.getInstance().timeInMillis.toDisplayFormat()
    val year = Calendar.getInstance().timeInMillis.toDisplayFormatYear()
    val month = Calendar.getInstance().timeInMillis.toDisplayFormatMonth()
    val day = Calendar.getInstance().timeInMillis.toDisplayFormatDay()
    val createdTime = Calendar.getInstance().timeInMillis
}