package com.sean.green.event.toEvent

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.sean.green.GreenApplication
import com.sean.green.R
import com.sean.green.data.*
import com.sean.green.data.FirebaseKey.Companion.COLLECTION_EVENT
import com.sean.green.data.source.GreenRepository
import com.sean.green.ext.*
import com.sean.green.login.UserManager
import com.sean.green.network.LoadApiStatus
import io.grpc.InternalChannelz.id
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ToEventViewModel(private val repository: GreenRepository) : ViewModel() {

    val time = MutableLiveData<String>()
    val location = MutableLiveData<String>()
    val introduction = MutableLiveData<String>()
    val content = MutableLiveData<String>()

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

    private val _navigateToHome = MutableLiveData<Boolean>()

    val navigateToHome: MutableLiveData<Boolean>
        get() = _navigateToHome


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun navigateToHome() {
        _navigateToHome.value = true
    }

    fun navigateToHomeAfterSend(needRefresh: Boolean = false) {
        _navigateToHome.value = needRefresh
    }

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

            val today = Calendar.getInstance().timeInMillis.toDisplayFormat()
//            val year = Calendar.getInstance().timeInMillis.toDisplayFormatYear()
//            val month = Calendar.getInstance().timeInMillis.toDisplayFormatMonth()
//            val day = Calendar.getInstance().timeInMillis.toDisplayFormatDay()
//            val createdTime = Calendar.getInstance().timeInMillis

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
                eventTime = eventYMD,
                content = content.value?.toString(),
                createdTime = Calendar.getInstance().timeInMillis,
                introduction = introduction.value?.toString(),
                location = location.value.toString(),
                today = today,
                image = userImage,
                userName = userName,
                email = userEmail,
                memberImage = listOf(userImage),
                member = listOf(userEmail),
                eventYear = eventYear,
                eventMonth = eventMonth,
                eventDay = eventDate,
                eventTimestamp = eventTimeStamp

            )

//            val addMemberImage = FirebaseFirestore.getInstance()
//                .collection(COLLECTION_EVENT).document(event.id).update("KEY_EVENT_MEMBER_IMAGE",
//                    FieldValue.arrayUnion(UserManager.user.image))

            Log.d("dueDate", "eventDate = ${dueDate.value}")

            when (val result =
                repository.addEvent2Firebase(COLLECTION_EVENT, newEventData)) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    navigateToHomeAfterSend(true)
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
                    _error.value = GreenApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                }
            }
        }
    }
}