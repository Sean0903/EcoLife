package com.sean.green.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sean.green.MainViewModel
import com.sean.green.challenge.ChallengeViewModel
import com.sean.green.data.source.GreenRepository
import com.sean.green.home.HomeViewModel
import com.sean.green.save.SaveViewModel

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

                isAssignableFrom(HomeViewModel::class.java) ->
                    HomeViewModel(greenRepository)

                isAssignableFrom(SaveViewModel::class.java) ->
                    SaveViewModel(greenRepository)

                isAssignableFrom(ChallengeViewModel::class.java) ->
                    ChallengeViewModel(greenRepository)

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