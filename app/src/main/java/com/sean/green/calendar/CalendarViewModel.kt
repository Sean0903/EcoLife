package com.sean.green.calendar

import android.R
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.applandeo.materialcalendarview.EventDay
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import java.util.ArrayList

class CalendarViewModel: ViewModel() {

    val event: MutableList<EventDay> = ArrayList()

//    var event = MutableLiveData<List<EventDay>>()

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


//    viewModelScope.launch {
//        withContext(Dispatchers.IO) {
//        }
//    }
//
//    for (calendar in calendarView.selectedDates) {
//        /**取得選定日之Date */
//        calendar.time = calendar.time
//        /**在event陣列中新增一個元素 */
//        event.add(EventDay(calendar, R.drawable.ic_input_get))
//        /**刷新介面 */
//        //下面這一行會造成crash Only the original thread that created a view hierarchy can touch its views.
//        //意思好像是只能在主線程更新UI
//        calendarView.setEvents(event)
//    }
//}
}