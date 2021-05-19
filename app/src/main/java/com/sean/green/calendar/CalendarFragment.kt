package com.sean.green.calendar

import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException
import com.sean.green.MainActivity
import com.sean.green.databinding.FragmentCalendarBinding
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.text.SimpleDateFormat
import java.util.*


class CalendarFragment: Fragment() {

    private lateinit var binding : FragmentCalendarBinding
    private val viewModel : CalendarViewModel by lazy {
        ViewModelProvider(this).get(CalendarViewModel::class.java)
    }
    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        (activity as MainActivity).dismissFabButton(true)

        binding.buttonGetTheDay.setOnClickListener {
            /**利用forEach迴圈找出指定元素 */
            for (calendar in calendarView.selectedDates) {
                val sdf = SimpleDateFormat("yyyy/MM/dd")
                Toast.makeText(context,sdf.format(calendar.time), Toast.LENGTH_LONG).show()
//                Toast.makeText(context, "請在各欄輸入完整訊息", Toast.LENGTH_LONG).show()
            }
        }


        binding.buttonToday.setOnClickListener { v ->
            /**取得今日之Date */
            val date = Date()
            val calendar: Calendar = Calendar.getInstance()
            val sdfY = SimpleDateFormat("yyyy")
            val sdfM = SimpleDateFormat("MM")
            val sdfD = SimpleDateFormat("dd")
            /**calender設置為今日 */
            calendar.set(
                sdfY.format(date).toInt(), sdfM.format(date).toInt() - 1
                , sdfD.format(date).toInt()
            )
            try {
                /**刷新介面 */
                calendarView.setDate(calendar)
            } catch (e: OutOfDateRangeException) {
                e.printStackTrace()
            }
        }


        val event: List<EventDay> = ArrayList()

        /**設置標記*/
        binding.buttonSetTarget.setOnClickListener { v ->
            Thread(Runnable {
                /**利用forEach迴圈找出指定元素 */
                for (calendar in calendarView.selectedDates) {
                    /**取得選定日之Date */
                    calendar.time = calendar.time
                    /**在event陣列中新增一個元素 */
                    event.plus(EventDay(calendar, R.drawable.ic_input_get))
//                        /**刷新介面 */
//                        calendarView.setEvents(event)
                }
            }).start()
        }



        return binding.root
    }
}