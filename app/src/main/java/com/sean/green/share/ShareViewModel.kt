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

//    private val _userImage = MutableLiveData<List<User>>()
//
//    val userImage: LiveData<List<User>>
//        get() = _userImage

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
        getShareData()
        getSaveDataForChart(UserManager.user.email,Share())
//        getUser(UserManager.user.email)
//        setSaveDataForChart()
    }

    fun getShareData() {
        coroutineScope.launch {

            val shareListForRecycleView = mutableListOf<Share>()

                _status.value = LoadApiStatus.LOADING

                val shareList = repository.getSharingData(COLLECTION_SHARE)
            Log.d("shareData","shareList = ${repository.getSharingData(COLLECTION_SHARE)}")

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



    fun getSaveDataForChart(userEmail: String,share: Share) {
        coroutineScope.launch {

            var today = Calendar.getInstance().timeInMillis

            val dataListForChart = mutableListOf<List<Save>>()

            for (i in 0..6) {

                _status.value = LoadApiStatus.LOADING
                val daysAgo = today.toDisplayFormat()
                val saveList2 = repository.getSaveDataForShareChart(userEmail,share,share.email,COLLECTION_SAVE, daysAgo)
                Log.d("days", "time = $daysAgo")
                today -= 85000000

                Log.d("viewModel","share.email = ${share.email}")

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

        Log.d("sean0903", "haha = ${ _saveDataSevenDays.value} ")

        var dailyPlastic: Int = 0
        var dailyPower: Int = 0
        var dailyCarbon: Int = 0

        for (i in 0..0) {
            for (sumOneDay in sevenDaysData as List<Save>) {
                dailyPlastic = dailyPlastic.plus(sumOneDay.plastic ?: 0)
                Log.d("sean0603", "saveDailyPlastic = ${dailyPlastic}")

                dailyPower = dailyPower.plus(sumOneDay.power ?: 0)
                Log.d("sean0603", "saveDailyPower = ${dailyPower}")

                dailyCarbon = dailyCarbon.plus(sumOneDay.carbon ?: 0)
                Log.d("sean0603", "saveDailyCarbon = ${dailyCarbon}")

                count++
                Log.d("seanCount","count = $count")
            }
        }

        plasticList.add(dailyPlastic)
        powerList.add(dailyPower)
        carbonList.add(dailyCarbon)

        Log.d("sean0903", " savePlasticList = $plasticList")
        Log.d("sean0903", " savePowerList = $powerList")
        Log.d("sean0903", " saveCarbonList = $carbonList")

    }

//    private fun getUser(userEmail: String) {
//
//        coroutineScope.launch {
//
//            _status.value = LoadApiStatus.LOADING
//
//            val result = repository.getUser(userEmail, COLLECTION_USERS)
//            Log.d("getUser", "repository.getUser =" +
//                    "${repository.getUser(userEmail, COLLECTION_USERS)}")
//
//            _userImage.value = when (result) {
//                is Result.Success -> {
//                    Log.d("calendarViewModel", "result.data = ${result.data}")
//                    _error.value = null
//                    _status.value = LoadApiStatus.DONE
//                    result.data
//                }
//                is Result.Fail -> {
//                    _error.value = result.error
//                    _status.value = LoadApiStatus.ERROR
//                    null
//                }
//                is Result.Error -> {
//                    _error.value = result.exception.toString()
//                    _status.value = LoadApiStatus.ERROR
//                    null
//                }
//                else -> {
//                    _error.value = GreenApplication.instance.getString(com.sean.green.R.string.you_know_nothing)
//                    _status.value = LoadApiStatus.ERROR
//                    null
//                }
//            }
//            _refreshStatus.value = false
//            Log.d("getUser", "_userImage.value = ${_userImage.value}")
//        }
//    }
}