package com.sean.green.calendar

import com.sean.green.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException
import com.sean.green.MainActivity
import com.sean.green.databinding.FragmentCalendarBinding
import com.sean.green.ext.getVmFactory
import com.sean.green.home.HomeViewModel
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class CalendarFragment: Fragment() {

    private lateinit var binding : FragmentCalendarBinding

    private val viewModel by viewModels<CalendarViewModel> { getVmFactory() }

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

//        binding.buttonGetTheDay.setOnClickListener {
//            /**利用forEach迴圈找出指定元素 */
//            for (calendar in calendarView.selectedDates) {
//                val sdf = SimpleDateFormat("yyyy/MM/dd")
//                Toast.makeText(context,sdf.format(calendar.time), Toast.LENGTH_LONG).show()
////                Toast.makeText(context, "請在各欄輸入完整訊息", Toast.LENGTH_LONG).show()
//            }
//        }
//
//
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

        /**設置標記*/
        binding.buttonSetTarget.setOnClickListener { v ->
            Thread(Runnable {
                /**利用forEach迴圈找出指定元素 */
                for (calendar in calendarView.selectedDates) {
                    /**取得選定日之Date */
                    calendar.time = calendar.time
                    /**在event陣列中新增一個元素 */
                    viewModel.event.add(EventDay(calendar, R.drawable.lighting))
                        /**刷新介面 */
                    //下面這一行會造成crash Only the original thread that created a view hierarchy can touch its views.
                    //意思好像是只能在主線程更新UI
                    GlobalScope.launch(Dispatchers.Main){
                        calendarView.setEvents(viewModel.event)
                    }
                }
            }).start()
        }

        /**設置標記*/  //1
        binding.button1.setOnClickListener { v ->
            Thread(Runnable {
                /**利用forEach迴圈找出指定元素 */
                for (calendar in calendarView.selectedDates) {
                    /**取得選定日之Date */
                    calendar.time = calendar.time
                    /**在event陣列中新增一個元素 */
                    viewModel.event.add(EventDay(calendar, R.drawable.dry_clean))
                    /**刷新介面 */
                    //下面這一行會造成crash Only the original thread that created a view hierarchy can touch its views.
                    //意思好像是只能在主線程更新UI
                    GlobalScope.launch(Dispatchers.Main){
                        calendarView.setEvents(viewModel.event)
                    }
                }
            }).start()
        }

        /**設置標記*/  //2
        binding.button2.setOnClickListener { v ->
            Thread(Runnable {
                /**利用forEach迴圈找出指定元素 */
                for (calendar in calendarView.selectedDates) {
                    /**取得選定日之Date */
                    calendar.time = calendar.time
                    /**在event陣列中新增一個元素 */
                    viewModel.event.add(EventDay(calendar, R.drawable.two_dots))
                    /**刷新介面 */
                    //下面這一行會造成crash Only the original thread that created a view hierarchy can touch its views.
                    //意思好像是只能在主線程更新UI
                    GlobalScope.launch(Dispatchers.Main){
                        calendarView.setEvents(viewModel.event)
                    }
                }
            }).start()
        }

        /**設置標記*/  //3
        binding.button3.setOnClickListener { v ->
            Thread(Runnable {
                /**利用forEach迴圈找出指定元素 */
                for (calendar in calendarView.selectedDates) {
                    /**取得選定日之Date */
                    calendar.time = calendar.time
                    /**在event陣列中新增一個元素 */
                    viewModel.event.add(EventDay(calendar, R.drawable.ellipsis))
                    /**刷新介面 */
                    //下面這一行會造成crash Only the original thread that created a view hierarchy can touch its views.
                    //意思好像是只能在主線程更新UI
                    GlobalScope.launch(Dispatchers.Main){
                        calendarView.setEvents(viewModel.event)
                    }
                }
            }).start()
        }

        /**解除標記*/
        binding.buttonCancelTarget.setOnClickListener { v ->
            Thread(Runnable {
                /**利用forEach迴圈找出指定元素 */
                for (calendar in calendarView.selectedDates) {
                    /**取得選定日之Date */
                    calendar.time = calendar.time
                    /**利用for迴圈找出指定元素之index */
                    for (i in 0 until viewModel.event.size) {
                        val select = calendar.timeInMillis
                        val target: Long = viewModel.event[i].calendar.timeInMillis
                        if (select == target) {
                            /**刪除指定元素 */
                            viewModel.event.removeAt(i)
                            /**刷新介面 */
                            GlobalScope.launch(Dispatchers.Main){
                                calendarView.setEvents(viewModel.event)
                            }
                            }
                        }
                    }
            }).start()
        }


//        val events: MutableList<EventDay> = ArrayList()
//
//        val calendar = Calendar.getInstance()
//        events.add(EventDay(calendar, R.drawable.ic_input_get))
//
//        events.add(EventDay(calendar, Drawable()))
//
//        events.add(EventDay(calendar, R.drawable.sample_icon, Color.parseColor("#228B22")))
//
//        val calendarView: CalendarView = findViewById(R.id.calendarView) as CalendarView
//        calendarView.setEvents(events)


        return binding.root
    }
}