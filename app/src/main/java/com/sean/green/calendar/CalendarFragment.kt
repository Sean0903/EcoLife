package com.sean.green.calendar

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.sean.green.GreenApplication
import com.sean.green.R
import com.sean.green.databinding.FragmentCalendarBinding
import com.sean.green.ext.TimeUtil
import com.sean.green.ext.getVmFactory
import com.sean.green.util.OneDayDecorator
import com.sean.green.util.SingleDateDecorator
import org.threeten.bp.LocalDate
import java.util.*


class CalendarFragment : Fragment() {

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

        binding = FragmentCalendarBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        widget = binding.calendarView

        var adapter = CalendarEventAdapter()
        binding.recyclerViewCalendar.adapter = adapter

        viewModel.selectedLiveEvent.observe(viewLifecycleOwner, Observer {
            Log.d("viewModel.selectedLiveEvent", "Sorted Event List : $it")
            it?.let {
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
                binding.viewModel = viewModel
            }
        })

        viewModel.allEvent.observe(viewLifecycleOwner, Observer { it ->
            it?.forEach {
                Log.d("calendarFragment", "eventsInCalendar = $it")
            }
        })

        // Get the current selected date
        widget.setOnDateChangedListener { _, date, selected ->
            if (selected) {
                oneDayDecorator.setDate(date.date)

                val selectedDate = TimeUtil.dateToStamp(date.date.toString(), Locale.TAIWAN)

                // Create a sorted list of event based on the current date
                viewModel.createdDailyEvent(selectedDate)

                Log.d(" GetTheCurrentSelectedDate", "date = $selectedDate")
            }
        }

        val localDate = LocalDate.now()

        widget.setCurrentDate(localDate)

        // Set Indecator of current date
        widget.setSelectedDate(localDate)

        // Add dots based on my events
        viewModel.allEvent.observe(viewLifecycleOwner, Observer {

            Log.d("calendarViewModel", "viewModel.allEvents.observe, it=$it")

            it?.let {

                it.forEach { event ->
                    val year = event.year.toInt()
                    Log.d("calendarFragment", "year = $year")

                    val month = event.month.toInt()
                    Log.d("calendarFragment", "month = $month")

                    val day = event.day.toInt()
                    Log.d("calendarFragment", "day = $day")

                    addDotDecoration(year, month, day)

                }

                viewModel.createdDailyEvent(
                    TimeUtil.dateToStamp(
                        localDate.toString(),
                        Locale.TAIWAN
                    )
                )
            }
        })

        binding.lottieCalendar.repeatCount = -1
        binding.lottieCalendar.playAnimation()

        return binding.root
    }

    internal fun addDotDecoration(year: Int, month: Int, day: Int) {
        widget.addDecorators(
            SingleDateDecorator(
                GreenApplication.appContext.applicationContext.getColor(R.color.colorBlue4),
                CalendarDay.from(year, month, day)
            )
        )
    }
}




