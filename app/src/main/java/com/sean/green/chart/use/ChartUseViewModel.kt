package com.sean.green.chart.use

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sean.green.GreenApplication
import com.sean.green.R
import com.sean.green.data.FirebaseKey
import com.sean.green.data.Result
import com.sean.green.data.Use
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

            val use7DayList = mutableListOf<Use>()
            val dataListForChart = mutableListOf<List<Use>>()

            for (i in 0..6) {

                _status.value = LoadApiStatus.LOADING
                val daysAgo = today.toDisplayFormat()
                val useList = repository.getUseDataForChart(
                    userEmail, FirebaseKey.COLLECTION_USE, daysAgo
                )
                val useList2 = repository.getUseDataForChart(
                    userEmail, FirebaseKey.COLLECTION_USE, daysAgo
                )
                Log.d("chartUseViewModel", "charUseTime = $daysAgo")
                today -= 86400000

                when (useList) {
                    is Result.Success -> {
                        use7DayList.addAll(useList.data)
                        _error.value = null
                        _status.value = LoadApiStatus.DONE
                    }
                    else -> {
                        _error.value =
                            GreenApplication.instance.getString(R.string.Please_try_again_later)
                        _status.value = LoadApiStatus.ERROR
                    }
                }

                Log.d("chartUseViewModel", "use7DayList.size1 = ${use7DayList.size}")

                _useDataSevenDays.value = when (useList2) {
                    is Result.Success -> {
                        dataListForChart.add(useList2.data)
                        _error.value = null
                        _status.value = LoadApiStatus.DONE
                        useList2.data
                    }
                    else -> {
                        _error.value =
                            GreenApplication.instance.getString(R.string.Please_try_again_later)
                        _status.value = LoadApiStatus.ERROR
                        null
                    }
                }

                Log.d("chartUseViewModel", "useDataSevenDays = ${_useDataSevenDays.value}")

                _refreshStatus.value = false
            }

            _useDataForRecycleView.value = use7DayList

            Log.d("chartUseViewModel", "use7DayList = ${use7DayList}")
            Log.i("chartUseViewModel", "use7DayList.size2 = ${use7DayList.size}")

            Log.d("chartUseViewModel", "useDataListForChart = ${dataListForChart}")
            Log.i("chartUseViewModel", "useDataListForChart.size = ${dataListForChart.size}")

            setChartTotalData(use7DayList)
        }
    }

    val plasticList = mutableListOf<Int>()
    val powerList = mutableListOf<Int>()
    val carbonList = mutableListOf<Int>()

    fun setUseDataForChart() {
        val useSevenDaysData = _useDataSevenDays.value

        Log.d("chartUseViewModel", "useSevenDaysData = ${_useDataSevenDays.value} ")

        var dailyPlastic = 0
        var dailyPower = 0
        var dailyCarbon = 0

        //if there are more than one num per day , it will be summed into a total
        for (i in 0..0) {
            for (sumOneDay in useSevenDaysData as List<Use>) {
                dailyPlastic = dailyPlastic.plus(sumOneDay.plastic ?: 0)
                Log.d("chartUseViewModel", "useDailyPlastic = ${dailyPlastic}")

                dailyPower = dailyPower.plus(sumOneDay.power ?: 0)
                Log.d("chartUseViewModel", "useDailyPower = ${dailyPower}")

                dailyCarbon = dailyCarbon.plus(sumOneDay.carbon ?: 0)
                Log.d("chartUseViewModel", "useDailyCarbon = ${dailyCarbon}")
            }
        }

        //make data become a list(7days)
        plasticList.add(dailyPlastic)
        powerList.add(dailyPower)
        carbonList.add(dailyCarbon)

        Log.d("chartUseViewModel", " usePlasticList = $plasticList")
        Log.d("chartUseViewModel", " usePowerList = $powerList")
        Log.d("chartUseViewModel", " useCarbonList = $carbonList")

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

        var date = ""

        for (use in uses) {
            Log.d("chartUseViewModel", "useDataPerDay = $use")
            if (use.today != date) {

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
            //if doc more than one , the plastic num will be added into a total
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