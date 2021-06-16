package com.sean.green.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.sean.green.GreenApplication

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
}