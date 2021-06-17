package com.sean.green.chart.save

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sean.green.GreenApplication
import com.sean.green.R
import com.sean.green.data.FirebaseKey.Companion.COLLECTION_SAVE
import com.sean.green.data.Result
import com.sean.green.data.Save
import com.sean.green.data.source.GreenRepository
import com.sean.green.ext.toDisplayFormat
import com.sean.green.login.UserManager
import com.sean.green.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
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

    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String?>()

    val error: LiveData<String?>
        get() = _error

    // status for the loading icon of swl
    private val _refreshStatus = MutableLiveData<Boolean>()

    init {
        getSaveDataForChart(UserManager.user.email)
    }

    fun getSaveDataForChart(userEmail: String) {
        coroutineScope.launch {

            var today = Calendar.getInstance().timeInMillis

            val save7DaysList = mutableListOf<Save>()
            val dataListForChart = mutableListOf<List<Save>>()

            for (i in 0..6) {

                _status.value = LoadApiStatus.LOADING
                val daysAgo = today.toDisplayFormat()
                val saveList = repository.getSaveDataForChart(userEmail, COLLECTION_SAVE, daysAgo)
                val saveList2 = repository.getSaveDataForChart(userEmail, COLLECTION_SAVE, daysAgo)
                Log.d("chartSaveViewModel", "chartSaveTime = $daysAgo")
                today -= 87000000

                when (saveList) {
                    is Result.Success -> {
                        save7DaysList.addAll(saveList.data)
                        _error.value = null
                        _status.value = LoadApiStatus.DONE
                    }
                    else -> {
                        _error.value =
                            GreenApplication.instance.getString(R.string.Please_try_again_later)
                        _status.value = LoadApiStatus.ERROR
                    }
                }

                Log.d("chartSaveViewModel", "save7DaysList = ${save7DaysList.size}")

                _saveDataSevenDays.value = when (saveList2) {
                    is Result.Success -> {
                        dataListForChart.add(saveList2.data)
                        _error.value = null
                        _status.value = LoadApiStatus.DONE
                        saveList2.data
                    }
                    else -> {
                        _error.value =
                            GreenApplication.instance.getString(R.string.Please_try_again_later)
                        _status.value = LoadApiStatus.ERROR
                        null
                    }
                }

                Log.d("chartSaveViewModel", "saveDataSevenDays = ${_saveDataSevenDays.value}")

                _refreshStatus.value = false
            }

            _saveDataForRecycleView.value = save7DaysList

            Log.d("chartSaveViewModel", "save7List = ${save7DaysList}")
            Log.i("chartSaveViewModel", "save7List = ${save7DaysList.size}")

            Log.d("chartSaveViewModel", "dataListForChart = ${dataListForChart}")
            Log.i("chartSaveViewModel", "dataListForChart = ${dataListForChart.size}")

            setChartTotalData(save7DaysList)
        }
    }

    val plasticList = mutableListOf<Int>()
    val powerList = mutableListOf<Int>()
    val carbonList = mutableListOf<Int>()

    fun setSaveDataForChart() {
        val sevenDaysData = _saveDataSevenDays.value

        Log.d("chartSaveViewModel", "setSaveDataForChart = ${_saveDataSevenDays.value} ")

        var dailyPlastic = 0
        var dailyPower = 0
        var dailyCarbon = 0

        for (i in 0..0) {
            for (sumOneDay in sevenDaysData as List<Save>) {
                dailyPlastic = dailyPlastic.plus(sumOneDay.plastic ?: 0)
                Log.d("chartSaveViewModel", "saveDailyPlastic = ${dailyPlastic}")

                dailyPower = dailyPower.plus(sumOneDay.power ?: 0)
                Log.d("chartSaveViewModel", "saveDailyPower = ${dailyPower}")

                dailyCarbon = dailyCarbon.plus(sumOneDay.carbon ?: 0)
                Log.d("chartSaveViewModel", "saveDailyCarbon = ${dailyCarbon}")
            }
        }

        plasticList.add(dailyPlastic)
        powerList.add(dailyPower)
        carbonList.add(dailyCarbon)

        Log.d("chartSaveViewModel", " savePlasticList = $plasticList")
        Log.d("chartSaveViewModel", " savePowerList = $powerList")
        Log.d("chartSaveViewModel", " saveCarbonList = $carbonList")
    }

    var showTotalSavePlastic = MutableLiveData<Int>()
    var showTotalSavePower = MutableLiveData<Int>()
    var showTotalSaveCarbon = MutableLiveData<Int>()

    var totalPlastic = 0
    var totalPower = 0
    var totalCarbon = 0

    fun setChartTotalData(saves: List<Save>) {
        val plasticList: MutableList<Int> = mutableListOf()
        val powerList: MutableList<Int> = mutableListOf()
        val carbonList: MutableList<Int> = mutableListOf()

        var plasticTotalForDay = 0
        var powerTotalForDay = 0
        var carbonTotalForDay = 0

        var date = ""

        for (save in saves) {
            Log.d("chartSaveViewModel", "saveDataPerDay = $save")
            if (save.today != date) {

                if (date.isNotEmpty()) {
                    //add into a list
                    plasticList.add(plasticTotalForDay)
                    powerList.add(powerTotalForDay)
                    carbonList.add(carbonTotalForDay)
                }
                date = save.today.toString()
                plasticTotalForDay = 0
                powerTotalForDay = 0
                carbonTotalForDay = 0

            }
            //if doc more than one , the plastic num will be added into one result
            plasticTotalForDay += save.plastic ?: 0
            powerTotalForDay += save.power ?: 0
            carbonTotalForDay += save.carbon ?: 0
            Log.w("testSave", "date = $date")
            Log.w("testSave", "plasticTotalForDay = $plasticTotalForDay")
        }
        //add last day into list
        plasticList.add(plasticTotalForDay)
        powerList.add(powerTotalForDay)
        carbonList.add(carbonTotalForDay)
        Log.d("testSave", "plasticList = $plasticList")

        totalPlastic = plasticList.sum()
        totalPower = powerList.sum()
        totalCarbon = carbonList.sum()

        showTotalSavePlastic.value = totalPlastic
        showTotalSavePower.value = totalPower
        showTotalSaveCarbon.value = totalCarbon

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}


