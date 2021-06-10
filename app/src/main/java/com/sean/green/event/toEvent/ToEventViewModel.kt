package com.sean.green.event.toEvent

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.sean.green.GreenApplication
import com.sean.green.R
import com.sean.green.data.*
import com.sean.green.data.source.GreenRepository
import com.sean.green.ext.*
import com.sean.green.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ToEventViewModel (private val repository: GreenRepository): ViewModel() {

    val time = MutableLiveData<String>()
    val location = MutableLiveData<String>()
    val introduction = MutableLiveData<String>()
    val content = MutableLiveData<String>()

    var dueDate = MutableLiveData<Long>().apply {
        value = android.icu.util.Calendar.getInstance().timeInMillis
        Log.d("dueDate","eventDate = $value")
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

    init {
        stampToDate(dueDate.value!!)
    }


    fun navigateToHomeAfterSend(needRefresh: Boolean = false) {
        _navigateToHome.value = needRefresh
    }

    fun stampToDate(time: Long): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        return simpleDateFormat.format(Date(time))
    }



    fun addEventData2Firebase(userEmail: String, userImage: String, userName: String) {

        coroutineScope.launch {

            val today = Calendar.getInstance().timeInMillis.toDisplayFormat()
            val year = Calendar.getInstance().timeInMillis.toDisplayFormatYear()
            val month = Calendar.getInstance().timeInMillis.toDisplayFormatMonth()
            val day = Calendar.getInstance().timeInMillis.toDisplayFormatDay()
            val createdTime = Calendar.getInstance().timeInMillis

//            val data = hashMapOf(
//                "event" to "event",
//                "introduction" to introduction.value?.toString(),
//                "day" to dueDate.value
//                "year" to
//                "createdTime" to Calendar.getInstance().timeInMillis
//                "day" to day,
//                "month" to month,
//                "year" to year,
//                "createdTime" to createdTime,
//            )

//            val saveTime = FirebaseFirestore.getInstance()
//                .collection(FirebaseKey.COLLECTION_USERS).document(userEmail).collection("greens")
//                .document(dueDate.value!!.toString()).set(data, SetOptions.merge())

            val newEventData = Event(
                time =  dueDate.value!!,
                content = content.value?.toString(),
                createdTime = Calendar.getInstance().timeInMillis,
                introduction = introduction.value?.toString(),
                location = location.value.toString(),
                today = today,
                image = userImage,
                userName = userName,
                email = userEmail
            )

            when (val result =
                repository.addEvent2Firebase(FirebaseKey.COLLECTION_SHARE, newEventData)) {
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