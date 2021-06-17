package com.sean.green.event.toEvent

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.sean.green.GreenApplication
import com.sean.green.R
import com.sean.green.data.Event
import com.sean.green.data.FirebaseKey
import com.sean.green.data.FirebaseKey.Companion.COLLECTION_EVENT
import com.sean.green.data.Result
import com.sean.green.data.User
import com.sean.green.data.source.GreenRepository
import com.sean.green.ext.TimeUtil
import com.sean.green.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class ToEventViewModel(private val repository: GreenRepository) : ViewModel() {

    var dueDate = MutableLiveData<Long>().apply {
        value = android.icu.util.Calendar.getInstance().timeInMillis
        Log.d("dueDate", "eventDate = ${value}")
    }

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _userImage = MutableLiveData<List<User>>()

    val userImage: LiveData<List<User>>
        get() = _userImage

    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String?>()

    val error: LiveData<String?>
        get() = _error

    // status for the loading icon of swl
    private val _refreshStatus = MutableLiveData<Boolean>()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    val time = MutableLiveData<String>()
    val location = MutableLiveData<String>()
    val introduction = MutableLiveData<String>()
    val content = MutableLiveData<String>()

    fun addEventData2Firebase(event: Event,userEmail: String, userImage: String, userName: String) {

        coroutineScope.launch {

            val eventTimeStamp = dueDate.value!!
            val eventYMD = TimeUtil.stampToYMD(eventTimeStamp)
            val eventYear = TimeUtil.stampToYear(eventTimeStamp)
            val eventMonth = TimeUtil.stampToMonthInt(eventTimeStamp)
            val eventDate = TimeUtil.stampToDay(eventTimeStamp)

            Log.d("eventYMD", "YMD = ${eventYMD}")
            Log.d("eventYear", "year = ${eventYear}")
            Log.d("eventMonth", "month = ${eventMonth}")
            Log.d("eventDate", "date = ${eventDate}")

            val data = hashMapOf(
                "event" to "event",
                "introduction" to introduction.value?.toString(),
                "year" to eventYear,
                "month" to eventMonth,
                "day" to eventDate,
                "createdTime" to eventTimeStamp,
            )

            val saveTime = FirebaseFirestore.getInstance()
                .collection(FirebaseKey.COLLECTION_USERS).document(userEmail).collection("greens")
                .document(eventYMD).set(data, SetOptions.merge())

            val newEventData = Event(
                eventYMD = eventYMD,
                content = content.value?.toString(),
                createdTime = Calendar.getInstance().timeInMillis,
                introduction = introduction.value?.toString(),
                location = location.value.toString(),
                hostImage = userImage,
                hostName = userName,
                hostEmail = userEmail,
                memberImages = listOf(userImage),
                members = listOf(userEmail),
                eventYear = eventYear,
                eventMonth = eventMonth,
                eventDay = eventDate,
                eventTimestamp = eventTimeStamp
            )

            Log.d("dueDate", "eventDate = ${dueDate.value}")

            when (val result =
                repository.addEvent2Firebase(COLLECTION_EVENT, newEventData)) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                }
                else -> {
                    _error.value = GreenApplication.instance.getString(R.string.Please_try_again_later)
                    _status.value = LoadApiStatus.ERROR
                }
            }
        }
    }
}