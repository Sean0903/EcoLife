package com.sean.green.save

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.sean.green.GreenApplication
import com.sean.green.R
import com.sean.green.data.*
import com.sean.green.data.FirebaseKey.Companion.COLLECTION_SAVE
import com.sean.green.data.FirebaseKey.Companion.COLLECTION_USERS
import com.sean.green.data.FirebaseKey.Companion.CREATEDTIME
import com.sean.green.data.FirebaseKey.Companion.DAY
import com.sean.green.data.FirebaseKey.Companion.MONTH
import com.sean.green.data.FirebaseKey.Companion.PATH_GREENS
import com.sean.green.data.FirebaseKey.Companion.SAVE
import com.sean.green.data.FirebaseKey.Companion.YEAR
import com.sean.green.data.source.GreenRepository
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

    //photo
    private val _isUploadPhoto = MutableLiveData<Boolean>()
    private val isUploadPhoto: LiveData<Boolean>
        get() = _isUploadPhoto

    val _photoUri = MutableLiveData<Uri>()
    val photoUri: LiveData<Uri>
        get() = _photoUri

    private val _date = MutableLiveData<Date>()
    val date: LiveData<Date>
        get() = _date

    private val _time = MutableLiveData<Time>()
    val time : LiveData<Time>
        get() = _time

    private fun setCurrentDate(date: Date){
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
                DAY to Util.day,
                MONTH to Util.month,
                YEAR to Util.year,
                CREATEDTIME to Util.createdTime,
                SAVE to SAVE
            )

            FirebaseFirestore.getInstance()
                .collection(COLLECTION_USERS).document(userEmail).collection(PATH_GREENS)
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

            val newArticleData = Article(
                content = content.value.toString(),
                image = photoUri.value.toString(),
                save = FirebaseKey.PHOTO_TAG_SAVE,
                createdTime = Calendar.getInstance().timeInMillis,
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

    //camera function
    fun setPhoto(photo: Uri){
        _photoUri.value = photo
    }

    fun uploadPhoto(){
        _isUploadPhoto.value = true
    }

}



