package com.sean.green.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sean.green.MainViewModel
import com.sean.green.data.source.GreenRepository

/**
 *
 * Factory for all ViewModels.
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val greenRepository: GreenRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(greenRepository)

//                isAssignableFrom(HomeViewModel::class.java) ->
//                    HomeViewModel(stylishRepository)
//
//                isAssignableFrom(ChartViewModel::class.java) ->
//                    ChartViewModel(stylishRepository)
//
//                isAssignableFrom(PaymentViewModel::class.java) ->
//                    PaymentViewModel(stylishRepository)
//
//                isAssignableFrom(LoginViewModel::class.java) ->
//                    LoginViewModel(stylishRepository)
//
//                isAssignableFrom(CheckoutSuccessViewModel::class.java) ->
//                    CheckoutSuccessViewModel(stylishRepository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}