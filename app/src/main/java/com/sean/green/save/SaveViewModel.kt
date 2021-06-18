package com.sean.green.save

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.sean.green.GreenApplication
import com.sean.green.R
import com.sean.green.data.Article
import com.sean.green.data.FirebaseKey.Companion.COLLECTION_SAVE
import com.sean.green.data.Result
import com.sean.green.data.Save
import com.sean.green.data.Sum
import com.sean.green.data.source.GreenRepository
import com.sean.green.ext.toDisplayFormat
import com.sean.green.ext.toDisplayFormatDay
import com.sean.green.ext.toDisplayFormatMonth
import com.sean.green.ext.toDisplayFormatYear
import com.sean.green.network.LoadApiStatus
import com.sean.green.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.sql.Time
import java.util.*


class SaveViewModel(private val repository: GreenRepository) : ViewModel() {

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _save = MutableLiveData<Save>().apply {
        value = Save(
        )
    }

    val save: LiveData<Save>
        get() = _save

    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String?>()

    val error: LiveData<String?>
        get() = _error

    private val _time = MutableLiveData<Time>()
    val time : LiveData<Time>
        get() = _time

    //相片功能data
    private val _date = MutableLiveData<Date>()
    val date: LiveData<Date>
        get() = _date

    private val _isUploadPhoto = MutableLiveData<Boolean>()
    private val isUploadPhoto: LiveData<Boolean>
        get() = _isUploadPhoto

    val _photoUri = MutableLiveData<Uri>()
    val photoUri: LiveData<Uri>
        get() = _photoUri

    //相片功能function
    fun setPhoto(photo: Uri){
        _photoUri.value = photo
    }

    fun uploadPhoto(){
        _isUploadPhoto.value = true
    }

    fun setCurrentDate(date: Date){
        _date.value = date
        _time.value = Time(date.time)
    }

    init {
        setCurrentDate(Date())
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    val plastic = MutableLiveData<String>()
    val power = MutableLiveData<String>()
    val carbon = MutableLiveData<String>()
    val content = MutableLiveData<String>()


    fun addSaveData2Firebase(userEmail: String) {

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

            val newSaveData = Sum(
                plastic = plastic.value?.toInt(),
                power = power.value?.toInt(),
                carbon = carbon.value?.toInt(),
                content = content.value?.toString(),
                createdTime = Calendar.getInstance().timeInMillis,
                today = Util.today
            )

            when (val result = repository.addData2Firebase(userEmail,COLLECTION_SAVE,newSaveData)) {
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



