package com.sean.green.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sean.green.data.source.GreenRepository
import com.sean.green.home.HomeViewModel

@Suppress("UNCHECKED_CAST")
class UserViewModelFactory (
    private val repository: GreenRepository,
    private val userId: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {

                isAssignableFrom(HomeViewModel::class.java) ->
                    HomeViewModel(repository, userId)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}