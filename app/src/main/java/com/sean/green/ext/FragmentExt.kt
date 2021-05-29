package com.sean.green.ext

import androidx.fragment.app.Fragment
import com.sean.green.GreenApplication
import com.sean.green.data.Save
import com.sean.green.factory.ViewModelFactory


fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as GreenApplication).greenRepository
    return ViewModelFactory(repository)
}

//fun Fragment.getVmFactory(save: Save): SaveViewModelFactory {
//    val repository = (requireContext().applicationContext as GreenApplication).greenRepository
//    return SaveViewModelFactory(repository, save)
//}
