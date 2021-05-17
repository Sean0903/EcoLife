package com.sean.green.save

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class SaveViewModel: ViewModel() {

    val plastic = MutableLiveData<String>()
    val power = MutableLiveData<String>()
    val carbon = MutableLiveData<String>()

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
}