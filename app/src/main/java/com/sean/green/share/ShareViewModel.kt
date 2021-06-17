package com.sean.green.share

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sean.green.GreenApplication
import com.sean.green.R
import com.sean.green.data.FirebaseKey.Companion.COLLECTION_SAVE
import com.sean.green.data.FirebaseKey.Companion.COLLECTION_SHARE
import com.sean.green.data.Result
import com.sean.green.data.Save
import com.sean.green.data.Share
import com.sean.green.data.source.GreenRepository
import com.sean.green.ext.toDisplayFormat
import com.sean.green.login.UserManager
import com.sean.green.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class ShareViewModel(private val repository: GreenRepository) : ViewModel() {


    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _shareDataForRecycleView = MutableLiveData<List<Share>>()
    val shareDataForRecycleView: LiveData<List<Share>>
        get() = _shareDataForRecycleView

    private val _saveDataSevenDays = MutableLiveData<List<Save>>()
    val saveDataSevenDays: LiveData<List<Save>>
        get() = _saveDataSevenDays

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
        getShareData()
        getSaveDataForChart(UserManager.user.email, Share())
    }

    private fun getShareData() {
        coroutineScope.launch {

            val shareListForRecycleView = mutableListOf<Share>()

            _status.value = LoadApiStatus.LOADING

            val shareList = repository.getSharingData(COLLECTION_SHARE)
            Log.d("shareViewModel", "shareList = ${repository.getSharingData(COLLECTION_SHARE)}")

            when (shareList) {
                is Result.Success -> {
                    shareListForRecycleView.addAll(shareList.data)
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

            _shareDataForRecycleView.value = shareListForRecycleView
        }
    }


    fun getSaveDataForChart(userEmail: String, share: Share) {
        coroutineScope.launch {

            var today = Calendar.getInstance().timeInMillis

            val dataListForChart = mutableListOf<List<Save>>()

            for (i in 0..6) {

                _status.value = LoadApiStatus.LOADING
                val daysAgo = today.toDisplayFormat()
                val saveList2 = repository.getSaveDataForShareChart(
                    userEmail,
                    share,
                    share.email,
                    COLLECTION_SAVE,
                    daysAgo
                )
                Log.d("shareViewModel", "time = $daysAgo")
                today -= 85000000

                Log.d("shareViewModel", "share.email = ${share.email}")

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
                Log.w("shareViewModel", "saveDataSevenDays = ${_saveDataSevenDays.value}")

                _refreshStatus.value = false
            }

            Log.d("shareViewModel", "dataListForChart = ${dataListForChart}")
            Log.i("shareViewModel", "dataListForChart = ${dataListForChart.size}")
        }
    }

    val plasticList = mutableListOf<Int>()
    val powerList = mutableListOf<Int>()
    val carbonList = mutableListOf<Int>()
    var count = 0

    fun setSaveDataForChart() {
        val sevenDaysData = _saveDataSevenDays.value

        Log.d("shareViewModel", "setSaveDataForChart = ${_saveDataSevenDays.value} ")

        var dailyPlastic = 0
        var dailyPower = 0
        var dailyCarbon = 0

        for (i in 0..0) {
            for (sumOneDay in sevenDaysData as List<Save>) {
                dailyPlastic = dailyPlastic.plus(sumOneDay.plastic ?: 0)
                Log.d("shareViewModel", "saveDailyPlastic = ${dailyPlastic}")

                dailyPower = dailyPower.plus(sumOneDay.power ?: 0)
                Log.d("shareViewModel", "saveDailyPower = ${dailyPower}")

                dailyCarbon = dailyCarbon.plus(sumOneDay.carbon ?: 0)
                Log.d("shareViewModel", "saveDailyCarbon = ${dailyCarbon}")

                count++
                Log.d("seanCount", "count = $count")
            }
        }

        plasticList.add(dailyPlastic)
        powerList.add(dailyPower)
        carbonList.add(dailyCarbon)

        Log.d("shareViewModel", " savePlasticList = $plasticList")
        Log.d("shareViewModel", " savePowerList = $powerList")
        Log.d("shareViewModel", " saveCarbonList = $carbonList")
    }
}