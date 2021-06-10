package com.sean.green.event


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sean.green.GreenApplication
import com.sean.green.R
import com.sean.green.data.*
import com.sean.green.data.source.GreenRepository
import com.sean.green.ext.toDisplayFormat
import com.sean.green.login.UserManager
import com.sean.green.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*


class EventViewModel(private val repository: GreenRepository) : ViewModel() {


    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _eventDataForRecycleView = MutableLiveData<List<Event>>()
    val eventDataForRecycleView: LiveData<List<Event>>
        get() = _eventDataForRecycleView

    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String?>()

    val error: LiveData<String?>
        get() = _error

    private val _navigateToHome = MutableLiveData<Boolean>()

    val navigateToHome: MutableLiveData<Boolean>
        get() = _navigateToHome

    // status for the loading icon of swl
    private val _refreshStatus = MutableLiveData<Boolean>()

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

    init {
        getEventData()
    }

    fun getEventData() {
        coroutineScope.launch {

            val eventListForRecycleView = mutableListOf<Event>()

            _status.value = LoadApiStatus.LOADING

            val eventList = repository.getEventData(FirebaseKey.COLLECTION_EVENT)
            Log.d("eventData","eventList = ${repository.getEventData(FirebaseKey.COLLECTION_EVENT)}")

            when (eventList) {
                is Result.Success -> {
                    eventListForRecycleView.addAll(eventList.data)
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                }
                else -> {
                    _error.value =
                        GreenApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR

                }
            }

            _refreshStatus.value = false

            _eventDataForRecycleView.value = eventListForRecycleView

        }
    }
}