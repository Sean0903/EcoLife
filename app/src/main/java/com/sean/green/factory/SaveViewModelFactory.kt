package com.sean.green.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sean.green.data.Save
import com.sean.green.data.source.GreenRepository
import com.sean.green.save.SaveViewModel

//@Suppress("UNCHECKED_CAST")
//class SaveViewModelFactory(
//    private val greenRepository: GreenRepository,
//    private val save: Save
//) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel?> create(modelClass: Class<T>) =
//        with(modelClass) {
//            when {
//                isAssignableFrom(SaveViewModel::class.java) ->
//                    SaveViewModel(greenRepository, save)
//
//                else ->
//                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
//            }
//        } as T
//}