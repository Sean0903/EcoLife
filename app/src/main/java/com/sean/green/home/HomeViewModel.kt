package com.sean.green.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sean.green.GreenApplication
import com.sean.green.R
import com.sean.green.data.Save
import com.sean.green.data.source.GreenRepository
import com.sean.green.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.sean.green.data.Result


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

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getSaveNumResult()
        Log.d("sean","getSaveNumResult = $_saveNum")
    }

    private fun getSaveNumResult() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getSaveNum()
            Log.d("seanHomeViewModelResult","repository.getSaveNum = ${repository.getSaveNum()}")

            _saveNum.value = when (result) {
                is Result.Success -> {
                    Log.d("seanHomeViewModel","result.data = ${result.data}")
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
            Log.d("seanSaveValue","saveValue = ${_saveNum.value}")
        }
    }

    var totalSavePlastic = 0.0f
    var totalSavePower = 0.0f
    var totalSaveCarbon = 0.0f
    var totalUsePlastic = 0.0f
    var totalUsePower = 0.0f
    var totalUseCarbon = 0.0f
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
