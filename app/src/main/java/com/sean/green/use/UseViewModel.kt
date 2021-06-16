package com.sean.green.use

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.sean.green.GreenApplication
import com.sean.green.R
import com.sean.green.data.Article
import com.sean.green.data.Result
import com.sean.green.data.Use
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

class UseViewModel(private val repository: GreenRepository): ViewModel() {

    val content = MutableLiveData<String>()

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val plastic = MutableLiveData<String>()
    val power = MutableLiveData<String>()
    val carbon = MutableLiveData<String>()

    private val _use = MutableLiveData<Use>().apply {
        value = Use(
        )
    }

    val use: LiveData<Use>
        get() = _use

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

    fun navigateToHome () {
        _navigateToHome.value = true
    }

    fun navigateToHomeAfterSend (needRefresh: Boolean = false) {
        _navigateToHome.value = needRefresh
    }

    fun addUseData2Firebase(userEmail: String) {

        coroutineScope.launch {

//            val userId = "ip29dDcJ24BtyGUzNlPE"

            val today = Calendar.getInstance().timeInMillis.toDisplayFormat()
            val year = Calendar.getInstance().timeInMillis.toDisplayFormatYear()
            val month = Calendar.getInstance().timeInMillis.toDisplayFormatMonth()
            val day = Calendar.getInstance().timeInMillis.toDisplayFormatDay()
            val createdTime = Calendar.getInstance().timeInMillis

            val data = hashMapOf(
                "day" to day,
                "month" to month,
                "year" to year,
                "createdTime" to createdTime,
                "use" to "use"
            )

            val saveTime = FirebaseFirestore.getInstance()
                .collection("users").document(userEmail).collection("greens")
                .document(today).set(data, SetOptions.merge())

            val newUseData = Use(
                plastic = plastic.value?.toInt(),
                power = power.value?.toInt(),
                carbon = carbon.value?.toInt(),
                createdTime = Calendar.getInstance().timeInMillis,
                today = today
//                id = document.id
            )

            when (val result = repository.addUseNum2Firebase(userEmail,newUseData)) {
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
                    _error.value = GreenApplication.instance.getString(R.string.Please_try_again_later)
                    _status.value = LoadApiStatus.ERROR
                }
            }
        }
    }

    fun addArticle2Firebase(userEmail: String) {

        coroutineScope.launch {

            val today = Calendar.getInstance().timeInMillis.toDisplayFormat()
            val year = Calendar.getInstance().timeInMillis.toDisplayFormatYear()
            val month = Calendar.getInstance().timeInMillis.toDisplayFormatMonth()
            val day = Calendar.getInstance().timeInMillis.toDisplayFormatDay()
            val createdTime = Calendar.getInstance().timeInMillis

//            val articleTimeStamp = Calendar.getInstance().timeInMillis
//            val articleHourAndMin =  TimeUtil.stampToHM(articleTimeStamp)

            val data = hashMapOf(
                "day" to day,
                "month" to month,
                "year" to year,
                "createdTime" to createdTime,
                "save" to "save"
            )

            val saveTime = FirebaseFirestore.getInstance()
                .collection("users").document(userEmail).collection("greens")
                .document(today).set(data, SetOptions.merge())

            val newArticleData = Article(
                content = content.value.toString(),
                createdTime = Calendar.getInstance().timeInMillis,
//                id = document.id
            )

            when (val result = repository.addArticle2Firebase(userEmail,newArticleData)) {
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
                    _error.value = GreenApplication.instance.getString(R.string.Please_try_again_later)
                    _status.value = LoadApiStatus.ERROR
                }
            }
        }
    }
}