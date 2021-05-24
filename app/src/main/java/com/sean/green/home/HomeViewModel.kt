package com.sean.green.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sean.green.App
import com.sean.green.GreenApplication
import com.sean.green.R
import com.sean.green.data.FirebaseKey.Companion.COLLECTION_SAVE
import com.sean.green.data.Save
import com.sean.green.data.source.GreenRepository
import com.sean.green.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.sean.green.data.Result
import com.sean.green.ext.FORMAT_YYYY_MM_DD
import com.sean.green.ext.toDateFormat
import java.sql.Timestamp
import java.util.*


class HomeViewModel(private val repository: GreenRepository): ViewModel() {

    val plastic = MutableLiveData<String>()
    val power = MutableLiveData<String>()
    val carbon = MutableLiveData<String>()


    private val _saveNum = MutableLiveData<List<Save>>()

    val saveNum: LiveData<List<Save>>
        get() = _saveNum

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
        getTotalSaveNum()
        Log.d("homeViewModel", "getSaveNumResult = $_saveNum")
    }

    private fun getSaveNumResult() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getSaveNum(COLLECTION_SAVE)
            Log.d(
                "homeViewModel", "repository.getSaveNum = ${repository.getSaveNum(COLLECTION_SAVE)}")

            _saveNum.value = when (result) {
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
            Log.d("homeViewModel", "saveValue = ${_saveNum.value}")
        }
    }


    var showTotalSavePlastic = MutableLiveData<Int>()
    var showTotalSavePower = MutableLiveData<Int>()
    var showTotalSaveCarbon = MutableLiveData<Int>()

    var totalSavePower = 0
    var totalSavePlastic = 0
    var totalSaveCarbon = 0
    var totalUsePlastic = 0
    var totalUsePower = 0
    var totalUseCarbon = 0

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
}

















//    private val _save = MutableLiveData<List<Save>>()
//
//    val save: LiveData<List<Save>>
//        get() = _save

//    fun getAndSetSaveToday(){
//
//        coroutineScope.launch {
//
//            val save = greenRepository.getObjects(
//                COLLECTION_SAVE
//            )
//
//            if (save.isNotEmpty()){
//
//                setDataSaveFromFirebase(save[0] as Save)
//
//            }
//        }
//
//    }


//    var totalPlastic = 0.0f
//    var totalPower = 0.0f
//    var totalCarbon = 0.0f

//    fun getDocument() {
//        // [START get_document]
//        val docRef = db.collection("green").document("2021")
//        docRef.get()
//            .addOnSuccessListener { document ->
//                if (document != null) {
//                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
//                } else {
//                    Log.d(TAG, "No such document")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d(TAG, "get failed with ", exception)
//            }
//        // [END get_document]
//    }
//
//
//
//    /**
//     * For catch data
//     */
//    private val citiesRef = db.collection("green")
//    private val list = citiesRef.orderBy("createdTime", Query.Direction.DESCENDING)
//    var defaultData = mutableListOf<Save>()
//    //將從firebase抓下來的資料暫存在mutableListOf<Save>()
//    var data = mutableListOf<Save>()

//    private fun getData() {
//        Log.d("sean", "inti data = $data")
//
//        db.collection("green")
//            .orderBy("createdTime", Query.Direction.DESCENDING)
//            .get()
//            .addOnSuccessListener { result ->
//                //★這是一個for loop
//                for (document in result) {
//                    Log.d("sean", "${document.id} -> ${document.data}")
//                    data.add(
//                        Save(
//                             plastic = document.data["plastic"].toString(),
//                            power = document.data["power"].toString(),
//                            carbon = document.data["carbon"].toString(),
//                        )
//                    )
//                    Log.d("sean", "form of data = $data")
//                    Log.d("sean","form of title = ${document.data["title"].toString()}")
//                    break
//                }
//                _save.value = data
//                Log.d("sean", "final = $data")
//                Log.d("sean", "_save.value = ${_save.value}")
//
//            }
//            .addOnFailureListener { exception ->
//                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
//            }
//
//    }
//
//
//    /**
//     * Observe data instantly
//     */
//    val personal =
//
//        db.collection("save")
//            .addSnapshotListener { snapshots, e ->
//                if (e != null) {
//                    Log.w("sean", "listen:error", e)
//                    return@addSnapshotListener
//                }
//
//                for (dc in snapshots!!.documentChanges) {
//                    when (dc.type) {
//                        DocumentChange.Type.ADDED -> {
//                            Log.d(
//                                "sean",
//                                "New Invitation Card: ${dc.document.data}"
//                            )
//                            var mock123 = dc.document.data
//                            Log.d("TAG", "mock = $mock123")
//                            getData()
//                        }
//                        DocumentChange.Type.MODIFIED -> {
//                            Log.d(
//                                "TAG",
//                                "Changed Data: ${dc.document.data}"
//                            )
//                        }
//                        DocumentChange.Type.REMOVED -> Log.d(
//                            "TAG",
//                            "Removed Invitation Card: ${dc.document.data}"
//                        )
//                    }
//                }
//            }


//    private val _isCalendarClicked = MutableLiveData<Boolean>()
//    val isCalendarClicked : LiveData<Boolean>
//        get() = _isCalendarClicked
//
//    fun calendarClicked(){
//        _isCalendarClicked.value = true
//    }
//
//    fun calendarClickedAgain(){
//        _isCalendarClicked.value = false
//    }
//
//    fun setCurrentDate(date: Date){
//        _date.value = date
//    }
//
//    private val _date = MutableLiveData<Date>()
//    val date : LiveData<Date>
//        get() = _date
