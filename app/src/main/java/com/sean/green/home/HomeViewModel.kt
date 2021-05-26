package com.sean.green.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sean.green.App
import com.sean.green.GreenApplication
import com.sean.green.R
import com.sean.green.data.Challenge
import com.sean.green.data.FirebaseKey.Companion.COLLECTION_CHALLENGE
import com.sean.green.data.FirebaseKey.Companion.COLLECTION_SAVE
import com.sean.green.data.FirebaseKey.Companion.COLLECTION_USE
import com.sean.green.data.Save
import com.sean.green.data.source.GreenRepository
import com.sean.green.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.sean.green.data.Result
import com.sean.green.data.Use
import java.sql.Timestamp
import java.util.*
import kotlin.math.absoluteValue


class HomeViewModel(private val repository: GreenRepository): ViewModel() {


    val plastic = MutableLiveData<String>()
    val power = MutableLiveData<String>()
    val carbon = MutableLiveData<String>()

    private val   _saveNum = MutableLiveData<List<Save>>()
    val   saveNum: LiveData<List<Save>>
        get() =   _saveNum

    private val _useNum = MutableLiveData<List<Use>>()
    val useNum: LiveData<List<Use>>
        get() = _useNum

    private val _challengeNum = MutableLiveData<List<Challenge>>()
    val challengeNum: LiveData<List<Challenge>>
        get() = _challengeNum

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status2 = MutableLiveData<LoadApiStatus>()

    val status2: LiveData<LoadApiStatus>
        get() = _status2

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    // status for the loading icon of swl
    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    private val _isCallDeleteAction = MutableLiveData<Boolean>()
    val isCallDeleteAction: LiveData<Boolean>
        get() = _isCallDeleteAction

    private val _date = MutableLiveData<Date>()
    val date: LiveData<Date>
        get() = _date

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    init {
        getChallengeNumResult()
        getTotalSaveNum()
        getNowChallengeNum()
        getTotalUseNum()
    }

    private fun getChallengeNumResult() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getChallengeNum(COLLECTION_CHALLENGE)
            Log.d(
                "homeViewModel", "repository.getChallengeNum = ${repository.getChallengeNum(COLLECTION_CHALLENGE)}")

            _challengeNum.value = when (result) {
                is Result.Success -> {
                    Log.d("homeViewModel", "result.data = ${result.data}")
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    result.data
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {
                    _error.value = GreenApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
            _refreshStatus.value = false
            Log.d("homeViewModel", "saveValue = ${_challengeNum.value}")
        }
    }



    var showTotalSavePlastic = MutableLiveData<Int>()
    var showTotalSavePower = MutableLiveData<Int>()
    var showTotalSaveCarbon = MutableLiveData<Int>()

    var totalSavePlastic = 0
    var totalSavePower = 0
    var totalSaveCarbon = 0

    fun getTotalSaveNum() {
        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING

            val saveList = repository.getSaveNum(COLLECTION_SAVE)

            Log.d("homeViewModel", "getTotalSaveNum = ${repository.getSaveNum(COLLECTION_SAVE)}")

            _saveNum.value = when (saveList) {

                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE

                    for (saving in saveList.data as List<Save> ) {
//                        totalSavePower += saving.power!!
                        totalSavePlastic = totalSavePlastic.plus(saving.plastic ?: 0)
                        totalSavePower = totalSavePower.plus(saving.power ?: 0)
                        totalSaveCarbon = totalSaveCarbon.plus(saving.carbon ?: 0)
//                        Log.d("homePage", "totalSavePower_135 = ${totalSavePower}")
                    }
//                    totalSavePower = totalSavePower
                    showTotalSavePlastic.value = totalSavePlastic
                    showTotalSavePower.value = totalSavePower
                    showTotalSaveCarbon.value = totalSaveCarbon
                    Log.d("homePage", " showTotalSavePlastic.value = ${showTotalSavePlastic.value}")
                    Log.d("homePage", "showTotalSavePower.value = ${showTotalSavePower.value}")
                    Log.d("homePage", "showTotalSaveCarbon.value = ${showTotalSaveCarbon.value}")


                    saveList.data
                }
                else -> {
                    _error.value = GreenApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
            _refreshStatus.value = false
        }
    }

    var showTotalUsePlastic = MutableLiveData<Int>()
    var showTotalUsePower = MutableLiveData<Int>()
    var showTotalUseCarbon = MutableLiveData<Int>()

    var totalUsePlastic = 0
    var totalUsePower = 0
    var totalUseCarbon = 0

    fun getTotalUseNum() {
        coroutineScope.launch {
            _status2.value = LoadApiStatus.LOADING

            val useList = repository.getUseNum(COLLECTION_USE)

            Log.d("homeViewModel", "getTotalSaveNum = ${repository.getUseNum(COLLECTION_USE)}")

            _useNum.value = when (useList) {

                is Result.Success -> {
                    _error.value = null
                    _status2.value = LoadApiStatus.DONE

                    for (using in useList.data as List<Use> ) {
                        totalUsePlastic = totalUsePlastic.plus(using.use_plastic ?: 0)
                        totalUsePower = totalUsePower.plus(using.use_power ?: 0)
                        totalUseCarbon = totalUseCarbon.plus(using.use_carbon ?: 0)
//                        Log.d("homePage", "totalSavePower_135 = ${totalSavePower}")
                    }
//                    totalSavePower = totalSavePower
                    showTotalUsePlastic.value = totalUsePlastic
                    showTotalUsePower.value = totalUsePower
                    showTotalUseCarbon.value = totalUseCarbon
                    Log.d("homePage", " showTotalUsePlastic.value = ${showTotalUsePlastic.value}")
                    Log.d("homePage", "showTotalUsePower.value = ${showTotalUsePower.value}")
                    Log.d("homePage", "showTotalUseCarbon.value = ${showTotalUseCarbon.value}")

                    useList.data
                }
                else -> {
                    _error.value = GreenApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
            _refreshStatus.value = false
        }
    }

    var nowChallengePlastic = 0
    var nowChallengePower = 0
    var nowChallengeCarbon = 0

    var showNowChallengePlastic = MutableLiveData<Int>()
    var showNowChallengePower = MutableLiveData<Int>()
    var showNowChallengeCarbon = MutableLiveData<Int>()

    fun getNowChallengeNum() {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val nowChallenge = repository.getChallengeNum(COLLECTION_CHALLENGE)

            Log.d("homeViewModel", "getNowChallengeNum = ${repository.getChallengeNum(COLLECTION_CHALLENGE)}")

            _challengeNum.value = when (nowChallenge) {

                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE

                    for (challenge in nowChallenge.data as List<Challenge> ) {
//                        totalSavePower += saving.power!!
                        nowChallengePlastic =  nowChallengePlastic.plus(challenge.challenge_plastic ?: 0)
                        nowChallengePower = nowChallengePower.plus(challenge.challenge_power ?: 0)
                        nowChallengeCarbon = nowChallengeCarbon.plus(challenge.challenge_carbon ?: 0)
                        Log.d("homePage", "nowChallengePlastic = ${nowChallengePlastic}")

                    }

                    showNowChallengePlastic.value = nowChallengePlastic.minus(totalSavePlastic).plus(totalUsePlastic)
                    showNowChallengePower.value = nowChallengePower.minus(totalSavePower).plus(totalUsePower)
                    showNowChallengeCarbon.value = nowChallengeCarbon.minus(totalSaveCarbon).plus(totalUseCarbon)

                    Log.d("homePage", " showNowChallengePlastic.value = ${showNowChallengePlastic.value}")
                    Log.d("homePage", " showNowChallengePower.value = ${showNowChallengePower.value}")
                    Log.d("homePage", " showNowChallengeCarbon.value = ${showNowChallengeCarbon.value}")

                    nowChallenge.data
                }
                else -> {
                    _error.value = GreenApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
            _refreshStatus.value = false
        }
    }
}



