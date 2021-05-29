package com.sean.green.ext

import com.sean.green.data.CalendarEvent

fun List<CalendarEvent>?.sortByTimeStamp (selectedTime: Long) : List<CalendarEvent> {
    return this?.filter {
        it?.let {
            selectedTime <= it.createdTime && it.createdTime < selectedTime + 86400000
        }
    }
        ?: listOf()
}