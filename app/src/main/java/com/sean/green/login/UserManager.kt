package com.sean.green.login

import android.content.Context
import com.sean.green.GreenApplication
import com.sean.green.data.User

object UserManager {

    private const val USER_DATA = "user_data"
    private const val USER_NAME = "user_name"
    private const val LAST_TIME_LOGIN_GOOGLE = "last_time_google"

    var userName: String? = null
        get() = GreenApplication.instance
            .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)
            .getString(USER_NAME, "")
        set(value) {
            field = when (value) {
                null -> {
                    GreenApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .remove(USER_NAME)
                        .apply()
                    null
                }
                else -> {
                    GreenApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .putString(USER_NAME, value)
                        .apply()
                    value
                }
            }
        }

    var lastTimeGoogle: String? = null
        get() = GreenApplication.instance
            .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)
            .getString(LAST_TIME_LOGIN_GOOGLE, "")
        set(value) {
            field = when (value) {
                null -> {
                    GreenApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .remove(LAST_TIME_LOGIN_GOOGLE)
                        .apply()
                    null
                }
                else -> {
                    GreenApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .putString(LAST_TIME_LOGIN_GOOGLE, value)
                        .apply()
                    value
                }
            }
        }
}