package com.sean.green.save

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sean.green.data.Save

//class SaveViewModelFactory(
//    private val save: Save,
//    private val application: Application
//) : ViewModelProvider.Factory {
//    @Suppress("unchecked_cast")
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(SaveViewModel::class.java)) {
//            return SaveViewModel(save, application) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}