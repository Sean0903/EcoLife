package com.sean.green.chart.save

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sean.green.GreenApplication
import com.sean.green.R
import com.sean.green.data.Challenge
import com.sean.green.data.FirebaseKey.Companion.COLLECTION_SAVE
import com.sean.green.data.FirebaseKey.Companion.USER_ID
import com.sean.green.data.Result
import com.sean.green.data.Save
import com.sean.green.data.Total
import com.sean.green.data.source.GreenRepository
import com.sean.green.ext.toDisplayFormat
import com.sean.green.network.LoadApiStatus
import kotlinx.coroutines.*
import java.util.*

class ChartSaveViewModel(private val repository: GreenRepository) : ViewModel() {

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _saveDataSevenDays = MutableLiveData<List<Save>>()
    val saveDataSevenDays: LiveData<List<Save>>
        get() = _saveDataSevenDays

    private val _saveDataForRecycleView = MutableLiveData<List<Save>>()
    val saveDataForRecycleView: LiveData<List<Save>>
        get() = _saveDataForRecycleView

    private val _total = MutableLiveData<List<Total>>()
    val total: LiveData<List<Total>>
        get() = _total

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
//        setSaveDataForChart()
    }


    fun getSaveDataForChart() {
        coroutineScope.launch {

            val daydocument = mutableListOf<String>()

//            daydocument.run { add(two) }

            var today = Calendar.getInstance().timeInMillis

            for (i in 0..6) {

                val daysAgo = today.toDisplayFormat()

                Log.d("dayDocument", "time = $daysAgo")

                _status.value = LoadApiStatus.LOADING

                val saveList = repository.getSaveDataForChart(COLLECTION_SAVE, USER_ID, daysAgo)

                today -= 80000000
//                92123841
                Log.d(
                    "chartSaveViewModel",
                    "getSaveDataForChartInViewModel = ${repository.getSaveDataForChart(
                        COLLECTION_SAVE, USER_ID, daysAgo
                    )}"
                )

                _saveDataSevenDays.value = when (saveList) {
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
                Log.d(
                    "chartSaveViewModel",
                    " _saveDataSevenDays.value = ${_saveDataSevenDays.value}"
                )
                _refreshStatus.value = false
            }
        }
    }


    val totalPlastic = MutableLiveData<Int>()
    val totalPower = MutableLiveData<Int>()
    val totalCarbon = MutableLiveData<Int>()

    val plasticList = mutableListOf<Int>()
    val powerList = mutableListOf<Int>()
    val carbonList = mutableListOf<Int>()

    fun setSaveDataForChart() {
            val sevenDaysData = _saveDataSevenDays.value

//        var totalSavePlastic = MutableLiveData<Int>()
//        var totalSavePower = MutableLiveData<Int>()
//        var totalSaveCarbon = MutableLiveData<Int>()

            var dailyPlastic: Int = 0
            var dailyPower: Int = 0
            var dailyCarbon: Int = 0

            for (i in 0..0) {
                for (sumOneDay in sevenDaysData as List<Save>) {
                    dailyPlastic = dailyPlastic.plus(sumOneDay.plastic ?: 0)
                    Log.d("sean0602", "dailyPlastic = ${dailyPlastic}")

                    dailyPower = dailyPower.plus(sumOneDay.power ?: 0)
                    Log.d("sean0602", "dailyPower = ${dailyPower}")

                    dailyCarbon = dailyCarbon.plus(sumOneDay.carbon ?: 0)
                    Log.d("sean0602", "dailyCarbon = ${dailyCarbon}")
                }
            }

            plasticList.add(dailyPlastic)
            powerList.add(dailyPower)
            carbonList.add(dailyCarbon)

            Log.d("sean0903", " plasticList = $plasticList")
            Log.d("sean0903", " powerList = $powerList")
            Log.d("sean0903", " carbonList = $carbonList")

        onCleared()
    }

    fun getOneWeekEverydayTotal() {

            val oneWeekTotalPlastic = plasticList[0] + plasticList[1]

            totalPlastic.value = oneWeekTotalPlastic

            Log.d("sean0903", "oneWeekTotalPlastic = ${totalPlastic.value} ")
    }

    fun setDataForRecycleView(){

        val sevenDaysData = _saveDataSevenDays.value
        Log.d("setDataForRecycleView","data = ${saveDataSevenDays.value}")

    }

}


