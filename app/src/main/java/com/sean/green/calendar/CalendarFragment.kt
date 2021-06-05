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
import com.sean.green.util.Logger
import com.sean.green.util.OneDayDecorator
import com.sean.green.util.SingleDateDecorator
import kotlinx.android.synthetic.main.fragment_calendar.*
import org.threeten.bp.LocalDate
import java.util.*


class CalendarFragment: Fragment() {

    private val viewModel by viewModels<CalendarViewModel> { getVmFactory() }

    private lateinit var binding: FragmentCalendarBinding

    private lateinit var widget: MaterialCalendarView

    private val oneDayDecorator: OneDayDecorator = OneDayDecorator()

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCalendarBinding.inflate(inflater,container,false)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        widget = binding.calendarView

        (activity as MainActivity).dismissFabButton(true)

        var adapter = CalendarEventAdapter()
        binding.recyclerView.adapter = adapter


        viewModel.allEvent.observe(viewLifecycleOwner, Observer { it ->
//            Log.d("events_in_calendar_page", "events = $it")
            it?.forEach {
                Log.d("events_in_calendar_page", "events = $it")

//                Log.d("events_in_calendar_page", "it.attendeesName.component1() = ${it.attendeesName.component1()}")
            }

        })
//
//        // Get the current selected date
//        widget.setOnDateChangedListener { _, date, selected ->
//            if (selected) {
//                oneDayDecorator.setDate(date.date)
//
//                val selectedDate = TimeUtil.dateToStamp(date.date.toString(), Locale.TAIWAN)
//
//                // Create a sorted list of event based on the current date
//                viewModel.createdDailyEvent(selectedDate)
//
//                Logger.d("$selectedDate")
//            }
//        }

//        viewModel.allEvent.observe(viewLifecycleOwner, Observer {
//            adapter.submitList(it)
//
//            Log.d("calendarViewModel","calendarEvent =$it")
//
//        })

        binding.lottieAnimationView.repeatCount = -1
        // start lottie
        binding.lottieAnimationView.playAnimation()

        viewModel.selectedLiveEvent.observe(viewLifecycleOwner, Observer {
            Logger.d("Sorted Event List : $it")
            it?.let {
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
                binding.viewModel = viewModel
            }
        })


        // Get the current selected date
        widget.setOnDateChangedListener { _, date, selected ->
            if (selected) {
                oneDayDecorator.setDate(date.date)

                val selectedDate = TimeUtil.dateToStamp(date.date.toString(), Locale.TAIWAN)

                // Create a sorted list of event based on the current date
                viewModel.createdDailyEvent(selectedDate)

                Logger.d("$selectedDate")
            }
        }










        val localDate = LocalDate.now()



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
                viewModel.createdDailyEvent(TimeUtil.dateToStamp(localDate.toString(), Locale.TAIWAN))
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
                GreenApplication.appContext.applicationContext.getColor(R.color.colorBlue4),
                CalendarDay.from(year, month, day)
            )
        )
    }
}




