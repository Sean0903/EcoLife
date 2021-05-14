package com.sean.green.use

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UseViewModel: ViewModel() {
    val plastic = MutableLiveData<String>()
    val power = MutableLiveData<String>()
    val carbon = MutableLiveData<String>()
}