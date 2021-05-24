package com.sean.green.ext

import com.sean.green.App
import com.sean.green.R
import java.text.SimpleDateFormat
import java.util.*

fun Date?.toDateFormat(dateFormat: Int): String {

    return SimpleDateFormat(
        when (dateFormat){
            FORMAT_MM_DD -> App.applicationContext().getString(
                R.string.simpledateformat_MM_dd
            )
            FORMAT_YYYY_MM -> App.applicationContext().getString(
                R.string.simpledateformat_yyyy_MM
            )
            FORMAT_YYYY_MM_DD -> App.applicationContext().getString(
                R.string.simpledateformat_yyyy_MM_dd
            )
            FORMAT_HH_MM -> App.applicationContext().getString(
                R.string.simpledateformat_HH_mm
            )
            FORMAT_HH_MM_SS_FFFFFFFFF -> App.applicationContext().getString(
                R.string.simpledateformat_HH_mm_ss_fffffffff, "000000000")
            FORMAT_YYYY_MM_DDHHMMSS -> App.applicationContext().getString(
                R.string.simpledateformat_yyyy_MM_dd_HHmmss
            )
            FORMAT_YYYY_MM_DD_HH_MM_SS_FFFFFFFFF -> App.applicationContext().getString(
                R.string.simpledateformat_yyyy_MM_dd_HH_mm_ss_fffffffff, "000000000")
            else -> null
        }
        , Locale.US).format(this)
}

const val FORMAT_MM_DD: Int = 0x01
const val FORMAT_YYYY_MM_DD: Int = 0x02
const val FORMAT_YYYY_MM: Int = 0x03
const val FORMAT_HH_MM: Int = 0x04
const val FORMAT_HH_MM_SS_FFFFFFFFF: Int = 0x05
const val FORMAT_YYYY_MM_DDHHMMSS: Int = 0x06
const val FORMAT_YYYY_MM_DD_HH_MM_SS_FFFFFFFFF: Int = 0x07
