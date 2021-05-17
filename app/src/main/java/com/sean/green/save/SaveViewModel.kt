package com.sean.green.save

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sean.green.data.Save
import com.sean.green.data.source.GreenRepository

//SaveViewModel(save: Save, app: Application): AndroidViewModel(app)
class SaveViewModel() : ViewModel() {


    val plastic = MutableLiveData<String>()
    val power = MutableLiveData<String>()
    val carbon = MutableLiveData<String>()



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
}