package com.sean.green.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sean.green.GreenApplication
import com.sean.green.R
import com.sean.green.data.*
import com.sean.green.data.FirebaseKey.Companion.COLLECTION_ARTICLE
import com.sean.green.data.FirebaseKey.Companion.COLLECTION_CHALLENGE
import com.sean.green.data.FirebaseKey.Companion.COLLECTION_SAVE
import com.sean.green.data.FirebaseKey.Companion.COLLECTION_USE
import com.sean.green.data.source.GreenRepository
import com.sean.green.login.UserManager
import com.sean.green.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class  HomeViewModel(private val repository: GreenRepository): ViewModel() {

    private val   _saveNum = MutableLiveData<List<Save>>()
    val saveNum: LiveData<List<Save>>
        get() =   _saveNum

    private val _useNum = MutableLiveData<List<Use>>()
    val useNum: LiveData<List<Use>>
        get() = _useNum

    private val _challengeNum = MutableLiveData<List<Challenge>>()
    val challengeNum: LiveData<List<Challenge>>
        get() = _challengeNum

    private val _articleDataForRecycleView = MutableLiveData<List<Article>>()
    val articleDataForRecycleView: LiveData<List<Article>>
        get() = _articleDataForRecycleView

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

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
        getTotalSaveNum(UserManager.user.email)
        getArticleData(UserManager.user.email)
    }

    var showTotalSavePlastic = MutableLiveData<Int>()
    var showTotalSavePower = MutableLiveData<Int>()
    var showTotalSaveCarbon = MutableLiveData<Int>()

    var totalSavePlastic = 0
    var totalSavePower = 0
    var totalSaveCarbon = 0

    private fun getTotalSaveNum(userEmail: String) {
        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING

            val saveList = repository.getSaveNum(userEmail,COLLECTION_SAVE)

            Log.d("homeViewModel", "getTotalSaveNum = " +
                    "${repository.getSaveNum(userEmail,COLLECTION_SAVE)}")

            Log.d("homePage", "challengeNum = $saveList")

            _saveNum.value = when (saveList) {

                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE

                    for (saving in saveList.data as List<Save> ) {
                        totalSavePlastic = totalSavePlastic.plus(saving.plastic ?: 0)
                        totalSavePower = totalSavePower.plus(saving.power ?: 0)
                        totalSaveCarbon = totalSaveCarbon.plus(saving.carbon ?: 0)
                        Log.d("homePage", "totalSavePlastic = ${totalSavePlastic}")
                        Log.d("homePage", "saveList = $saveList")
                    }
                    showTotalSavePlastic.value = totalSavePlastic
                    showTotalSavePower.value = totalSavePower
                    showTotalSaveCarbon.value = totalSaveCarbon
                    Log.d("homePage", " showTotalSavePlastic.value = ${showTotalSavePlastic.value}")
                    Log.d("homePage", "showTotalSavePower.value = ${showTotalSavePower.value}")
                    Log.d("homePage", "showTotalSaveCarbon.value = ${showTotalSaveCarbon.value}")

                    saveList.data
                }
                else -> {
                    _error.value = GreenApplication.instance.getString(R.string.Please_try_again_later)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
            Log.d("homeViewModel", "saveList = $saveList")
            _refreshStatus.value = false
            getTotalUseNum(UserManager.user.email)
        }
    }

    var showTotalUsePlastic = MutableLiveData<Int>()
    var showTotalUsePower = MutableLiveData<Int>()
    var showTotalUseCarbon = MutableLiveData<Int>()

    var totalUsePlastic = 0
    var totalUsePower = 0
    var totalUseCarbon = 0

    private fun getTotalUseNum(userEmail: String) {
        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING

            val useList = repository.getUseNum(userEmail,COLLECTION_USE)

            Log.d("homeViewModel", "getTotalUseNum = ${repository.getUseNum(userEmail,COLLECTION_USE)}")
            Log.d("homeViewModel", "useList = $useList")

            _useNum.value = when (useList) {

                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE

                    for (using in useList.data as List<Use> ) {
                        totalUsePlastic = totalUsePlastic.plus(using.plastic ?: 0)
                        totalUsePower = totalUsePower.plus(using.power ?: 0)
                        totalUseCarbon = totalUseCarbon.plus(using.carbon ?: 0)
                        Log.d("homePage", "totalUsePlastic = ${totalUsePlastic}")
                    }
                    showTotalUsePlastic.value = totalUsePlastic
                    showTotalUsePower.value = totalUsePower
                    showTotalUseCarbon.value = totalUseCarbon
                    Log.d("homePage", "showTotalUsePlastic.value = ${showTotalUsePlastic.value}")
                    Log.d("homePage", "showTotalUsePower.value = ${showTotalUsePower.value}")
                    Log.d("homePage", "showTotalUseCarbon.value = ${showTotalUseCarbon.value}")

                    useList.data
                }
                else -> {
                    _error.value = GreenApplication.instance.getString(R.string.Please_try_again_later)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
            _refreshStatus.value = false
            getNowChallengeNum(UserManager.user.email)
        }
    }

    var showNowChallengePlastic = MutableLiveData<Int>()
    var showNowChallengePower = MutableLiveData<Int>()
    var showNowChallengeCarbon = MutableLiveData<Int>()

    var challengePlastic = 10
    var challengePower = 10
    var challengeCarbon = 10

    private fun getNowChallengeNum(userEmail: String) {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val nowChallenge = repository.getChallengeNum(userEmail,COLLECTION_CHALLENGE)
            Log.d("homeViewModel", "getNowChallengeNum = ${repository.getChallengeNum(userEmail,COLLECTION_CHALLENGE)}")

            _challengeNum.value = when (nowChallenge) {

                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE

                    for (challenge in nowChallenge.data as List<Challenge> ) {
                        challengePlastic =  challengePlastic.plus(challenge.plastic ?: 0)
                        challengePower = challengePower.plus(challenge.power ?: 0)
                        challengeCarbon = challengeCarbon.plus(challenge.carbon ?: 0)
                    }

                    showNowChallengePlastic.value = challengePlastic.minus(totalSavePlastic).plus(totalUsePlastic)
                    showNowChallengePower.value = challengePower.minus(totalSavePower).plus(totalUsePower)
                    showNowChallengeCarbon.value = challengeCarbon.minus(totalSaveCarbon).plus(totalUseCarbon)

                    Log.d("homePage", " showNowChallengePlastic.value = ${showNowChallengePlastic.value}")
                    Log.d("homePage", " showNowChallengePower.value = ${showNowChallengePower.value}")
                    Log.d("homePage", " showNowChallengeCarbon.value = ${showNowChallengeCarbon.value}")

                    nowChallenge.data
                }
                else -> {
                    _error.value = GreenApplication.instance.getString(R.string.Please_try_again_later)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
            _refreshStatus.value = false
        }
    }

    private fun getArticleData(userEmail: String) {
        coroutineScope.launch {

            val articleListForRecycleView = mutableListOf<Article>()

            _status.value = LoadApiStatus.LOADING

            val articleList = repository.getArticle(userEmail, COLLECTION_ARTICLE)
            Log.d("homeViewModel","articleList = ${repository.getArticle(userEmail, COLLECTION_ARTICLE)}")

            when (articleList) {
                is Result.Success -> {
                    articleListForRecycleView.addAll(articleList.data)
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

            _articleDataForRecycleView.value = articleListForRecycleView
            Log.d("homeViewModel","_articleDataForRecycleView.value = ${_articleDataForRecycleView.value}")
        }
    }
}



