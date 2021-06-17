package com.sean.green.challenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.sean.green.GreenApplication
import com.sean.green.R
import com.sean.green.data.Article
import com.sean.green.data.Challenge
import com.sean.green.data.Result
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

class ChallengeViewModel(private val repository: GreenRepository) : ViewModel() {

    val plastic = MutableLiveData<String>()
    val power = MutableLiveData<String>()
    val carbon = MutableLiveData<String>()
    val content = MutableLiveData<String>()

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String?>()

    val error: LiveData<String?>
        get() = _error

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun addChallengeData2Firebase(userEmail: String) {

        coroutineScope.launch {

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
                "challenge" to "challenge"
            )

            val saveTime = FirebaseFirestore.getInstance()
                .collection("users").document(userEmail).collection("greens")
                .document(today).set(data, SetOptions.merge())

            val newChallengeData = Challenge(
                plastic = plastic.value?.toInt(),
                power = power.value?.toInt(),
                carbon = carbon.value?.toInt(),
                createdTime = Calendar.getInstance().timeInMillis,
            )

            when (val result = repository.addChallenge2Firebase(userEmail, newChallengeData)) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
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
                    _error.value =
                        GreenApplication.instance.getString(R.string.Please_try_again_later)
                    _status.value = LoadApiStatus.ERROR
                }
            }
        }
    }

    fun addArticle2Firebase(userEmail: String) {

        coroutineScope.launch {

            val newArticleData = Article(
                content = content.value?.toString(),
                createdTime = Calendar.getInstance().timeInMillis,
            )

            when (val result = repository.addArticle2Firebase(userEmail, newArticleData)) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
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
                    _error.value =
                        GreenApplication.instance.getString(R.string.Please_try_again_later)
                    _status.value = LoadApiStatus.ERROR
                }
            }
        }
    }
}
