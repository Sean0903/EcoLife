package com.sean.green.save

import android.util.Log
import androidx.lifecycle.LiveData
import com.sean.green.data.Result
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.sean.green.GreenApplication
import com.sean.green.R
import com.sean.green.data.Save
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


class SaveViewModel(private val repository: GreenRepository) : ViewModel() {


    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val plastic = MutableLiveData<String>()
    val power = MutableLiveData<String>()
    val carbon = MutableLiveData<String>()

    private val _save = MutableLiveData<Save>().apply {
        value = Save(
        )
    }

    val save: LiveData<Save>
        get() = _save

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


    fun addSaveData2Firebase() {

        coroutineScope.launch {

            val userId = "ip29dDcJ24BtyGUzNlPE"

            val today = Calendar.getInstance().timeInMillis.toDisplayFormat()
            Log.d("seanSaveTime","today = $today")

            val saveTimeData = hashMapOf(
                "day" to  (Calendar.getInstance().timeInMillis.toDisplayFormatDay()),
            "month" to (Calendar.getInstance().timeInMillis.toDisplayFormatMonth()),
            "year" to (Calendar.getInstance().timeInMillis.toDisplayFormatYear()),
            "createdTime" to (Calendar.getInstance().timeInMillis),
                "save" to ("save")
            )
            val saveTime = FirebaseFirestore.getInstance()
                .collection("users").document(userId).collection("greens")
                .document(today).set(saveTimeData)

            Log.d("seanSaveTime","saveTimeData = $saveTimeData ")

            val newSaveData = Save(
                plastic = plastic.value?.toInt(),
                power = power.value?.toInt(),
                carbon = carbon.value?.toInt(),
                createdTime = Calendar.getInstance().timeInMillis,
//                id = document.id
            )

            when (val result = repository.addSaveNum2Firebase(newSaveData, userId)) {
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



