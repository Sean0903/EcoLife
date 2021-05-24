package com.sean.green.save

import androidx.lifecycle.LiveData
import com.sean.green.data.Result
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.sean.green.GreenApplication
import com.sean.green.R
import com.sean.green.data.Save
import com.sean.green.data.source.GreenRepository
import com.sean.green.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*


class SaveViewModel(private val repository: GreenRepository) : ViewModel() {


    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val plastic = MutableLiveData<String>()
    val power = MutableLiveData<String>()
    val carbon = MutableLiveData<String>()

//    private val _user = MutableLiveData<User>()
//
//    val user: LiveData<User>
//        get() = _user

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



    fun addSaveData2Firebase() {

        coroutineScope.launch {

            val saveNum = FirebaseFirestore.getInstance()
                .collection("green")
            val document = saveNum.document()

            val newSaveData = Save(
                plastic = plastic.value?.toInt()!!,
                power = power.value?.toInt()!!,
                carbon = carbon.value?.toInt()!!,
                createdTime = Calendar.getInstance().timeInMillis,
                id = document.id
            )

            val washingtonRef =
                saveNum.document("user")
            washingtonRef.update("email", FieldValue.arrayUnion("sean@school.appworks.tw"))
            washingtonRef.update("id", FieldValue.arrayUnion("sean0903"))
            washingtonRef.update("name", FieldValue.arrayUnion( "梁凱翔"))

            when (val result = repository.addSaveNum2Firebase(newSaveData)) {
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
                    _error.value = GreenApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                }
            }
        }
    }




}



