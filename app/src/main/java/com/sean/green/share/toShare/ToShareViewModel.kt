package com.sean.green.share.toShare

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.sean.green.GreenApplication
import com.sean.green.R
import com.sean.green.data.Result
import com.sean.green.data.Share
import com.sean.green.data.source.GreenRepository
import com.sean.green.ext.toDisplayFormat
import com.sean.green.ext.toDisplayFormatDay
import com.sean.green.ext.toDisplayFormatMonth
import com.sean.green.ext.toDisplayFormatYear
import com.sean.green.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class ToShareViewModel(private val repository: GreenRepository): ViewModel() {

    val name = MutableLiveData<String>()
    val achievement = MutableLiveData<String>()
    val time = MutableLiveData<String>()
    val content = MutableLiveData<String>()

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String?>()

    val error: LiveData<String?>
        get() = _error

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

    fun addSharingData2Firebase() {

        coroutineScope.launch {

            val userId = "ip29dDcJ24BtyGUzNlPE"

            val today = Calendar.getInstance().timeInMillis.toDisplayFormat()
            val year = Calendar.getInstance().timeInMillis.toDisplayFormatYear()
            val month = Calendar.getInstance().timeInMillis.toDisplayFormatMonth()
            val day = Calendar.getInstance().timeInMillis.toDisplayFormatDay()
            val createdTime = Calendar.getInstance().timeInMillis

            val data = hashMapOf(
                "day" to day,
                "month" to month,
                "year" to year,
                "createdTime" to createdTime,
                "share" to "share"
            )

            val saveTime = FirebaseFirestore.getInstance()
                .collection("users").document(userId).collection("greens")
                .document(today).set(data, SetOptions.merge())

            val newShareData = Share(
                name = name.value?.toString(),
                achievement = achievement.value?.toString(),
                time = time.value?.toString(),
                content = content.value?.toString(),
                createdTime = Calendar.getInstance().timeInMillis,
                today = today
            )

            when (val result = repository.addSharing2Firebase(newShareData, userId)) {
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