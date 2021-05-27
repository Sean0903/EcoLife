package com.sean.green.calendar

import android.R
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.applandeo.materialcalendarview.EventDay
import com.sean.green.GreenApplication
import com.sean.green.data.CalendarEvent
import com.sean.green.data.Challenge
import com.sean.green.data.FirebaseKey
import com.sean.green.data.FirebaseKey.Companion.COLLECTION_CHALLENGE
import com.sean.green.data.FirebaseKey.Companion.PATH_GREENS
import com.sean.green.data.FirebaseKey.Companion.USER_ID
import com.sean.green.data.Result
import com.sean.green.data.source.GreenRepository
import com.sean.green.network.LoadApiStatus
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.coroutines.*
import java.util.ArrayList

class CalendarViewModel(private val repository: GreenRepository): ViewModel() {

    val event: MutableList<EventDay> = ArrayList()

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _calendarEvent = MutableLiveData<List<CalendarEvent>>()
    val calendarEvent: LiveData<List<CalendarEvent>>
        get() = _calendarEvent

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    // status for the loading icon of swl
    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    init {
        getCalendarEvent()
    }

    private fun getCalendarEvent() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getCalendarEvent()
            Log.d("calendarViewModel", "repository.getCalendarEvent =" +
                    "${repository.getCalendarEvent()}")

            _calendarEvent.value = when (result) {
                is Result.Success -> {
                    Log.d("calendarViewModel", "result.data = ${result.data}")
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    result.data
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {
                    _error.value = GreenApplication.instance.getString(com.sean.green.R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
            _refreshStatus.value = false
            Log.d("calendarViewModel", "calendarData = ${_calendarEvent.value}")
        }
    }


}