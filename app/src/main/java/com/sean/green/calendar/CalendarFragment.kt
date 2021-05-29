package com.sean.green.calendar

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.sean.green.GreenApplication
import com.sean.green.MainActivity
import com.sean.green.R
import com.sean.green.databinding.FragmentCalendarBinding
import com.sean.green.ext.TimeUtil
import com.sean.green.ext.getVmFactory
import com.sean.green.util.SingleDateDecorator
import kotlinx.android.synthetic.main.fragment_calendar.*
import org.threeten.bp.LocalDate
import java.util.*


class CalendarFragment: Fragment() {

    private val viewModel by viewModels<CalendarViewModel> { getVmFactory() }

    private lateinit var binding: FragmentCalendarBinding

    private lateinit var widget: MaterialCalendarView

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCalendarBinding.inflate(inflater,container,false)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        (activity as MainActivity).dismissFabButton(true)

        val localDate = LocalDate.now()

        widget = binding.calendarView

        widget.setCurrentDate(localDate)

        // Set Indecator of current date
        widget.setSelectedDate(localDate)

        // Add dots based on my events
        viewModel.allEvent.observe(viewLifecycleOwner, Observer {

            Log.d("calendarViewModel","viewModel.allEvents.observe, it=$it")

            it?.let {

                it.forEach { event ->
                    val year = event.year.toInt()
                    Log.d("calendarFragment","year = $year")

                    val month = event.month.toInt()
                    Log.d("calendarFragment","month = $month")

                    val day = event.day.toInt()
                    Log.d("calendarFragment","day = $day")

                    addDotDecoration(year, month, day)


                }
                viewModel.createDailyEvent(TimeUtil.dateToStamp(localDate.toString(), Locale.TAIWAN))
            }
        })

        viewModel.navigationToPostDialog.observe(viewLifecycleOwner, Observer { date ->

            date?.let {
            }

        })

//        binding.buttonToday.setOnClickListener { v ->
//            //back to today
//            val date = Date()
//            val calendar: Calendar = Calendar.getInstance()
//            val sdfY = SimpleDateFormat("yyyy")
//            val sdfM = SimpleDateFormat("MM")
//            val sdfD = SimpleDateFormat("dd")
//            //calender set to today
//            calendar.set(
//                sdfY.format(date).toInt(), sdfM.format(date).toInt() - 1
//                , sdfD.format(date).toInt()
//            )
//            try {
//                //refresh
//                calendarView.selectedDates(calendar)
//            } catch (e: OutOfDateRangeException) {
//                e.printStackTrace()
//            }
//        }

        return binding.root
    }

    private fun addDotDecoration(year: Int, month: Int, day: Int) {
        widget.addDecorators(
            SingleDateDecorator(
                GreenApplication.appContext.applicationContext.getColor(R.color.brightBlue2),
                CalendarDay.from(year, month, day)
            )
        )
    }
}




