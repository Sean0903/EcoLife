package com.sean.green.chart.use

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

class ChartUseViewModel(private val repository: GreenRepository) : ViewModel() {

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _useDataSevenDays = MutableLiveData<List<Use>>()
    val useDataSevenDays: LiveData<List<Use>>
        get() = _useDataSevenDays

    private val _useDataForRecycleView = MutableLiveData<List<Use>>()
    val useDataForRecycleView: LiveData<List<Use>>
        get() = _useDataForRecycleView

    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String?>()

    val error: LiveData<String?>
        get() = _error

    // status for the loading icon of swl
    private val _refreshStatus = MutableLiveData<Boolean>()

    init {
        getUseDataForChart(UserManager.user.email)
    }


    fun getUseDataForChart(userEmail: String) {
        coroutineScope.launch {

            var today = Calendar.getInstance().timeInMillis

            val use7List = mutableListOf<Use>()
            val dataListForChart = mutableListOf<List<Use>>()

            for (i in 0..6) {

                _status.value = LoadApiStatus.LOADING
                val daysAgo = today.toDisplayFormat()
                val useList = repository.getUseDataForChart(
                    userEmail, FirebaseKey.COLLECTION_USE, daysAgo)
                val useList2 = repository.getUseDataForChart(
                    userEmail, FirebaseKey.COLLECTION_USE, daysAgo)
                Log.d("days", "charUsetime = $daysAgo")
                today -= 87000000

                when (useList) {
                    is Result.Success -> {
                        use7List.addAll(useList.data)
                        _error.value = null
                        _status.value = LoadApiStatus.DONE
                    }
                    else -> {
                        _error.value =
                            GreenApplication.instance.getString(R.string.you_know_nothing)
                        _status.value = LoadApiStatus.ERROR

                    }
                }

                Log.d("chartUseViewModel", "use7List = ${use7List.size}")

                _useDataSevenDays.value = when (useList2) {
                    is Result.Success -> {
                        dataListForChart.add(useList2.data)
                        _error.value = null
                        _status.value = LoadApiStatus.DONE
                        useList2.data
                    }
                    else -> {
                        _error.value =
                            GreenApplication.instance.getString(R.string.you_know_nothing)
                        _status.value = LoadApiStatus.ERROR
                        null
                    }
                }

                Log.d("chartUseViewModel", "useDataSevenDays = ${_useDataSevenDays.value}")

                _refreshStatus.value = false
            }

            _useDataForRecycleView.value = use7List

            Log.d("chartUseViewModel", "use7List = ${use7List}")
            Log.i("chartUseViewModel", "use7List = ${use7List.size}")

            Log.d("chartUseViewModel", "dataListForChart = ${dataListForChart}")
            Log.i("chartUseViewModel", "dataListForChart = ${dataListForChart.size}")

            setChartTotalData(use7List)
        }
    }

    val plasticList = mutableListOf<Int>()
    val powerList = mutableListOf<Int>()
    val carbonList = mutableListOf<Int>()

    fun setUseDataForChart() {
        val sevenDaysData = _useDataSevenDays.value

        Log.d("sean0903", "setUseDataForChart = ${ _useDataSevenDays.value} ")

        var dailyPlastic: Int = 0
        var dailyPower: Int = 0
        var dailyCarbon: Int = 0

        for (i in 0..0) {
            for (sumOneDay in sevenDaysData as List<Use>) {
                dailyPlastic = dailyPlastic.plus(sumOneDay.plastic ?: 0)
                Log.d("sean0603", "useDailyPlastic = ${dailyPlastic}")

                dailyPower = dailyPower.plus(sumOneDay.power ?: 0)
                Log.d("sean0603", "useDailyPower = ${dailyPower}")

                dailyCarbon = dailyCarbon.plus(sumOneDay.carbon ?: 0)
                Log.d("sean0603", "useDailyCarbon = ${dailyCarbon}")
            }
        }

        plasticList.add(dailyPlastic)
        powerList.add(dailyPower)
        carbonList.add(dailyCarbon)

        Log.d("sean0903", " usePlasticList = $plasticList")
        Log.d("sean0903", " usePowerList = $powerList")
        Log.d("sean0903", " useCarbonList = $carbonList")

    }

    var showTotalUsePlastic = MutableLiveData<Int>()
    var showTotalUsePower = MutableLiveData<Int>()
    var showTotalUseCarbon = MutableLiveData<Int>()

    var totalPlastic = 0
    var totalPower = 0
    var totalCarbon = 0

    fun setChartTotalData(uses: List<Use>) {
        val plasticList: MutableList<Int> = mutableListOf()
        val powerList: MutableList<Int> = mutableListOf()
        val carbonList: MutableList<Int> = mutableListOf()

        var plasticTotalForDay = 0
        var powerTotalForDay = 0
        var carbonTotalForDay = 0

        var date: String = ""

        for (use in uses) {
            Log.d("testUse", "use = $use")
            if (use.today != date) {
                Log.i("testUse", "use.today != date")

                if (date.isNotEmpty()) {
                    //add into a list
                    plasticList.add(plasticTotalForDay)
                    powerList.add(powerTotalForDay)
                    carbonList.add(carbonTotalForDay)
                }
                date = use.today.toString()
                plasticTotalForDay = 0
                powerTotalForDay = 0
                carbonTotalForDay = 0

            }
            //if doc more than one , the plastic num will be added into one result
            plasticTotalForDay += use.plastic ?: 0
            powerTotalForDay += use.power ?: 0
            carbonTotalForDay += use.carbon ?: 0
            Log.w("testUse", "date = $date")
            Log.w("testUse", "plasticTotalForDay = $plasticTotalForDay")
        }
        //add last day into list
        plasticList.add(plasticTotalForDay)
        powerList.add(powerTotalForDay)
        carbonList.add(carbonTotalForDay)
        Log.d("testUse", "plasticList = $plasticList")

        totalPlastic = plasticList.sum()
        totalPower = powerList.sum()
        totalCarbon = carbonList.sum()

        showTotalUsePlastic.value = totalPlastic
        showTotalUsePower.value = totalPower
        showTotalUseCarbon.value = totalCarbon

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}