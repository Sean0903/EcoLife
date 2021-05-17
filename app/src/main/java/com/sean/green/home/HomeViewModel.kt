package com.sean.green.home

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.appworks.school.stylish.util.ServiceLocator.greenRepository
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.sean.green.data.FirebaseKey.Companion.COLLECTION_SAVE
import com.sean.green.data.Save
import com.sean.green.data.source.GreenRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel(private val greenRepository: GreenRepository): ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _dataSaveFromFirebase = MutableLiveData<Save>()
    val dataSaveFromFirebase : LiveData<Save>
        get() = _dataSaveFromFirebase

    private fun setDataSaveFromFirebase (save : Save){
        _dataSaveFromFirebase.value = save
    }

    var db = FirebaseFirestore.getInstance()

    private val _save = MutableLiveData<List<Save>>()

    val save: LiveData<List<Save>>
        get() = _save

    fun getAndSetSaveToday(){

        coroutineScope.launch {

            val save = greenRepository.getObject(
                COLLECTION_SAVE
            )

            if (save.isNotEmpty()){

                setDataSaveFromFirebase(save[0] as Save)

            }
        }

    }


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


}