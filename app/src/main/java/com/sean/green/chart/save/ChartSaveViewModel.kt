package com.sean.green.chart.save

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sean.green.GreenApplication
import com.sean.green.R
import com.sean.green.data.Challenge
import com.sean.green.data.FirebaseKey
import com.sean.green.data.FirebaseKey.Companion.COLLECTION_SAVE
import com.sean.green.data.FirebaseKey.Companion.USER_ID
import com.sean.green.data.Result
import com.sean.green.data.Save
import com.sean.green.data.source.GreenRepository
import com.sean.green.ext.toDisplayFormat
import com.sean.green.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.absoluteValue

class ChartSaveViewModel(private val repository: GreenRepository) : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _dayDocument = MutableLiveData<List<Long>>()

    val dayDocument: LiveData<List<Long>>
        get() = _dayDocument

    private val _saveDataForChart = MutableLiveData<List<Save>>()
    val saveDataForChart: LiveData<List<Save>>
        get() = _saveDataForChart

    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String?>()

    val error: LiveData<String?>
        get() = _error

    // status for the loading icon of swl
    private val _refreshStatus = MutableLiveData<Boolean>()

    init {
        getSaveDataForChart()
    }


    fun getSaveDataForChart() {
        coroutineScope.launch {

            val daydocument = mutableListOf<String>()

//            daydocument.run { add(two) }

            var today = Calendar.getInstance().timeInMillis

            for (i in 0..5) {

                val daysAgo = today.toDisplayFormat()

                Log.d("dayDocument","time = $daysAgo")

                _status.value = LoadApiStatus.LOADING

                val saveList = repository.getSaveDataForChart(COLLECTION_SAVE, USER_ID, daysAgo)

                today -= 92123841

                Log.d("chartSaveViewModel",
                    "getSaveDataForChartInViewModel = ${repository.getSaveDataForChart(
                        COLLECTION_SAVE, USER_ID, daysAgo)}")

                Log.d("chartSaveViewModel"," _saveDataForChart.value = ${ _saveDataForChart.value}")
                _saveDataForChart.value = when (saveList) {
                    is Result.Success -> {
                        _error.value = null
                        _status.value = LoadApiStatus.DONE

                        saveList.data
                    }
                    else -> {
                        _error.value =
                            GreenApplication.instance.getString(R.string.you_know_nothing)
                        _status.value = LoadApiStatus.ERROR
                        null
                    }
                }
                _refreshStatus.value = false
            }
        }
    }
}