package com.sean.green.event


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sean.green.GreenApplication
import com.sean.green.R
import com.sean.green.data.Event
import com.sean.green.data.FirebaseKey
import com.sean.green.data.Result
import com.sean.green.data.source.GreenRepository
import com.sean.green.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class EventViewModel(private val repository: GreenRepository) : ViewModel() {

    private val _eventDataForRecycleView = MutableLiveData<List<Event>>()
    val eventDataForRecycleView: LiveData<List<Event>>
        get() = _eventDataForRecycleView

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

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

    init {
        getEventData()
    }

    fun getEventData() {
        coroutineScope.launch {

            val eventListForRecycleView = mutableListOf<Event>()

            _status.value = LoadApiStatus.LOADING

            val eventList = repository.getEventData(FirebaseKey.COLLECTION_EVENT)
            Log.d(
                "eventData",
                "eventList = ${repository.getEventData(FirebaseKey.COLLECTION_EVENT)}"
            )

            when (eventList) {
                is Result.Success -> {
                    eventListForRecycleView.addAll(eventList.data)
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                }
                else -> {
                    _error.value =
                        GreenApplication.instance.getString(R.string.Please_try_again_later)
                    _status.value = LoadApiStatus.ERROR

                }
            }

            _refreshStatus.value = false

            _eventDataForRecycleView.value = eventListForRecycleView
            Log.d(
                "eventViewModel",
                "_eventDataForRecycleView.value = ${_eventDataForRecycleView.value}"
            )
        }
    }

    fun addMemberToEvent(event: Event, userEmail: String, userImage: String) {

        coroutineScope.launch {
            val result = repository.addEventMember(event.id, userEmail, userImage)
        }
    }

    fun addEventInfo2UserFirebase(event: Event, userEmail: String) {

        coroutineScope.launch {

            when (val result =
                repository.addEventInfo2UserFirebase(event, event.id, event.eventYMD, userEmail)) {
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
                    _error.value =
                        GreenApplication.instance.getString(R.string.Please_try_again_later)
                    _status.value = LoadApiStatus.ERROR
                }
            }
        }
    }
}