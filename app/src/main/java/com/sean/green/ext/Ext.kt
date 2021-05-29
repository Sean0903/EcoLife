package com.sean.green.ext

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


@RequiresApi(Build.VERSION_CODES.N)
fun Long.toDisplayFormat(): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.TAIWAN).format(this)
}

fun Long.toDisplayFormatYear(): String {
    return SimpleDateFormat("yyyy", Locale.TAIWAN).format(this)
}

fun Long.toDisplayFormatMonth(): String {
    return SimpleDateFormat("MM", Locale.TAIWAN).format(this)
}

fun Long.toDisplayFormatDay(): String {
    return SimpleDateFormat("dd", Locale.TAIWAN).format(this)
}

object TimeUtil {

    @JvmStatic
    fun stampToYear(time: Long): String {
        val simpleDateFormat = java.text.SimpleDateFormat("yyyy")
        return simpleDateFormat.format(Date(time))
    }

    @JvmStatic
    fun stampToMonthInt(time: Long): String {
        val simpleDateFormat = java.text.SimpleDateFormat("MM")
        return simpleDateFormat.format(Date(time))
    }

    @JvmStatic
    fun stampToDay(time: Long): String {
        val simpleDateFormat = java.text.SimpleDateFormat("dd")
        return simpleDateFormat.format(Date(time))
    }

    @JvmStatic
    fun dateToStamp(date: String, locale: Locale): Long {
        val simpleDateFormat = java.text.SimpleDateFormat("yyyy-MM-dd", locale)
        return simpleDateFormat.parse(date).time
    }

}


//fun Date?.toDateFormat(dateFormat: Int): String {
//
//
//
//    return SimpleDateFormat(
//        when (dateFormat){
//            FORMAT_MM_DD -> App.applicationContext().getString(
//                R.string.simpledateformat_MM_dd
//            )
//            FORMAT_YYYY_MM -> App.applicationContext().getString(
//                R.string.simpledateformat_yyyy_MM
//            )
//            FORMAT_YYYY_MM_DD -> App.applicationContext().getString(
//                R.string.simpledateformat_yyyy_MM_dd
//            )
//            FORMAT_HH_MM -> App.applicationContext().getString(
//                R.string.simpledateformat_HH_mm
//            )
//            FORMAT_HH_MM_SS_FFFFFFFFF -> App.applicationContext().getString(
//                R.string.simpledateformat_HH_mm_ss_fffffffff, "000000000")
//            FORMAT_YYYY_MM_DDHHMMSS -> App.applicationContext().getString(
//                R.string.simpledateformat_yyyy_MM_dd_HHmmss
//            )
//            FORMAT_YYYY_MM_DD_HH_MM_SS_FFFFFFFFF -> App.applicationContext().getString(
//                R.string.simpledateformat_yyyy_MM_dd_HH_mm_ss_fffffffff, "000000000")
//            else -> null
//        }
//        , Locale.US).format(this)
//}
//
//const val FORMAT_MM_DD: Int = 0x01
//const val FORMAT_YYYY_MM_DD: Int = 0x02
//const val FORMAT_YYYY_MM: Int = 0x03
//const val FORMAT_HH_MM: Int = 0x04
//const val FORMAT_HH_MM_SS_FFFFFFFFF: Int = 0x05
//const val FORMAT_YYYY_MM_DDHHMMSS: Int = 0x06
//const val FORMAT_YYYY_MM_DD_HH_MM_SS_FFFFFFFFF: Int = 0x07
