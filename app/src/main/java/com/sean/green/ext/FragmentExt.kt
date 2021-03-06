package com.sean.green.ext

import androidx.fragment.app.Fragment
import com.sean.green.GreenApplication
import com.sean.green.factory.ViewModelFactory


fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as GreenApplication).greenRepository
    return ViewModelFactory(repository)
}

