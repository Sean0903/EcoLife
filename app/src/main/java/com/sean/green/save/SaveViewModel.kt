package com.sean.green.save

import androidx.lifecycle.LiveData
import com.sean.green.data.Result
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

            val newSaveData = Save(
                plastic = plastic.value!!,
                power = power.value!!,
                carbon = carbon.value!!
            )

            val saveNum = FirebaseFirestore.getInstance()
                .collection("green")
            val document = saveNum.document()
            val data = hashMapOf(
                "user" to hashMapOf(
                    "email" to "sean@school.appworks.tw",
                    "id" to "sean0903",
                    "name" to "凱翔"
                ),
                "createdTime" to Calendar.getInstance().timeInMillis,
                "id" to document.id
            )
            document.set(data)

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



//    // Handle navigation to homepage
//    private val _navigateToHome = MutableLiveData<Save>()
//
//    val navigateToHome: LiveData<Save>
//        get() = _navigateToHome
//
//
//    fun navigateToHome(save: Save) {
//        _navigateToHome.value = save
//    }
//
//    fun onDetailNavigated() {
//        _navigateToHome.value = null
//    }

//    //viewModel裡可以保存LiveData，LiveData可以自動通知Observer，來完成activity和fragment的更新
//    private val _save = MutableLiveData<Save?>()
//
//    // The external LiveData for the SelectedProperty
//    val save: LiveData<Save?>
//        get() = _save

// Initialize the _selectedProperty MutableLiveData
//    init {
//        _save.value = save
//    }


//    fun addSaveData2fire() {
//        val green = FirebaseFirestore.getInstance()
//            .collection("green")
//        val document = green.document()
//        val data = hashMapOf(
////            "author" to hashMapOf(
////                "email" to "wayne@school.appworks.tw",
////                "id" to "waynechen323",
////                "name" to "AKA小安老師"
////            ),
//            "plastic" to plastic,
//            "power" to power,
////            "createdTime" to Calendar.getInstance().timeInMillis,
//            "carbon" to carbon,
////            "category" to category
//        )
//        document.set(data)
//    }