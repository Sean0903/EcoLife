package com.sean.green.factory

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Factory for all ViewModels.
 */
//@Suppress("UNCHECKED_CAST")
//class ViewModelFactory constructor(
//    private val greenRepository: GreenRepository
//) : ViewModelProvider.NewInstanceFactory() {
//
//    override fun <T : ViewModel> create(modelClass: Class<T>) =
//        with(modelClass) {
//            when {
//                isAssignableFrom(MainViewModel::class.java) ->
//                    MainViewModel(greenRepository)

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
//                else ->
//                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
//            }
//        } as T
//}