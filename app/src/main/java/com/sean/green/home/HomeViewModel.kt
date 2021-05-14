package com.sean.green.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sean.green.data.Save

class HomeViewModel: ViewModel() {

    private val _save = MutableLiveData<List<Save>>()

    val save: LiveData<List<Save>>
        get() = _save
}