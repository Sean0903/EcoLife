package com.sean.green.challenge

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChallengeViewModel: ViewModel() {

    val plastic = MutableLiveData<String>()
    val power = MutableLiveData<String>()
    val carbon = MutableLiveData<String>()
}