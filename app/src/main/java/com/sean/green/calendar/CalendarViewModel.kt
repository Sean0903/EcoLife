package com.sean.green.calendar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sean.green.GreenApplication
import com.sean.green.data.CalendarEvent
import com.sean.green.data.FirebaseKey.Companion.PATH_GREENS
import com.sean.green.data.Result
import com.sean.green.data.source.GreenRepository
import com.sean.green.ext.TimeUtil
import com.sean.green.ext.sortByTimeStamp
import com.sean.green.login.UserManager
import com.sean.green.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import java.util.*

class CalendarViewModel(private val repository: GreenRepository): ViewModel() {



    //Get all events with user as attendee
    private var _allEvents = MutableLiveData<List<CalendarEvent>>()

    val allEvent : LiveData<List<CalendarEvent>>
        get() = _allEvents

    var allLiveEvents = MutableLiveData<List<CalendarEvent>>()

    //Selected date for safe arg
    private val _navigationToPostDialog = MutableLiveData<Long>()

    val navigationToPostDialog : LiveData<Long>
        get() = _navigationToPostDialog

    //Query Selected Events
    private var _selectedEvents = MutableLiveData<List<CalendarEvent>>()

    val selectedEvent : LiveData<List<CalendarEvent>>
        get() = _selectedEvents

    val selectedLiveEvent = MutableLiveData<List<CalendarEvent>>()





//    val event: MutableList<EventDay> = ArrayList()

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

//    private val _calendarEvent = MutableLiveData<List<CalendarEvent>>()
//    val calendarEvent: LiveData<List<CalendarEvent>>
//        get() = _calendarEvent

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
        getCalendarEvent(UserManager.user.email)
        todayDate()
    }

    private fun getCalendarEvent(userEmail: String) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getCalendarEvent(userEmail,PATH_GREENS)
            Log.d("calendarViewModel", "repository.getChallengeNum =" +
                    "${repository.getCalendarEvent(userEmail,PATH_GREENS)}")

            _allEvents.value = when (result) {
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
                    _error.value = GreenApplication.instance.getString(com.sean.green.R.string.Please_try_again_later)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
            _refreshStatus.value = false
            Log.d("calendarViewModel", "calendarData = ${_allEvents.value}")
        }
    }

    fun createdDailyEvent (toTimeStamp: Long) {
        selectedLiveEvent.value = allEvent.value.sortByTimeStamp(toTimeStamp)
        _navigationToPostDialog.value = toTimeStamp

        Log.d("calendarViewModel"," selectedLiveEvent.value = ${selectedLiveEvent.value}")
        Log.d("createdDailyEvent","allLiveEvents.value = ${allEvent.value}")

    }

    private fun todayDate() {
        _navigationToPostDialog.value = TimeUtil.dateToStamp(LocalDate.now().toString(), Locale.TAIWAN)
    }
}