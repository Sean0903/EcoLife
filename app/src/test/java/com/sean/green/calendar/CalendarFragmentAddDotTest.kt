package com.sean.green.calendar

import org.junit.Test

class CalendarFragmentAddDotTest {

    private val year: Int = 2021
    private val month: Int = 6
    private val day: Int = 21

    @Test
    fun addDot_inOneDay_returnSuccess() {

        val fragment = CalendarFragment()

        val result = fragment.addDotDecoration(year,month,day)

    }

}