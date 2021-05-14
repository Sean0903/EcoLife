package com.sean.green.save

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SaveViewModel: ViewModel() {

    val plastic = MutableLiveData<String>()
    val power = MutableLiveData<String>()
    val carbon = MutableLiveData<String>()
}