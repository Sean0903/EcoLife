package com.sean.green.use

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.sean.green.GreenApplication
import com.sean.green.R
import com.sean.green.data.Article
import com.sean.green.data.FirebaseKey.Companion.COLLECTION_USE
import com.sean.green.data.Result
import com.sean.green.data.Sum
import com.sean.green.data.source.GreenRepository
import com.sean.green.network.LoadApiStatus
import com.sean.green.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class UseViewModel(private val repository: GreenRepository) : ViewModel() {

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

    val content = MutableLiveData<String>()
    val plastic = MutableLiveData<String>()
    val power = MutableLiveData<String>()
    val carbon = MutableLiveData<String>()

    fun addUseData2Firebase(userEmail: String) {

        coroutineScope.launch {

            val data = hashMapOf(
                "day" to Util.day,
                "month" to Util.month,
                "year" to Util.year,
                "createdTime" to Util.createdTime,
                "use" to "use"
            )

            val saveTime = FirebaseFirestore.getInstance()
                .collection("users").document(userEmail).collection("greens")
                .document(Util.today).set(data, SetOptions.merge())

            val newUseData = Sum(
                plastic = plastic.value?.toInt(),
                power = power.value?.toInt(),
                carbon = carbon.value?.toInt(),
                createdTime = Calendar.getInstance().timeInMillis,
                today = Util.today
            )

            when (val result = repository.addData2Firebase(userEmail,COLLECTION_USE,newUseData)) {
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
            val data = hashMapOf(
                "day" to Util.day,
                "month" to Util.month,
                "year" to Util.year,
                "createdTime" to Util.createdTime,
                "save" to "save"
            )

            val saveTime = FirebaseFirestore.getInstance()
                .collection("users").document(userEmail).collection("greens")
                .document(Util.today).set(data, SetOptions.merge())

            val newArticleData = Article(
                content = content.value.toString(),
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