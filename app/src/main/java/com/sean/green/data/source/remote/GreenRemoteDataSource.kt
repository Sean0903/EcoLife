package com.sean.green.data.source.remote

import com.sean.green.data.source.GreenDataSource

object GreenRemoteDataSource : GreenDataSource {

//    override suspend fun getProductDetails(id: Long?): Result<DetailResult> {
//        if (!isInternetConnected()) {
//            return Result.Fail(getString(R.string.internet_not_connected))
//        }
//
//        return try {
//            // this will run on a thread managed by Retrofit
//            val listResult = StylishApi.retrofitService.getProductDetails(id)
//
//            listResult.error?.let {
//                return Result.Fail(it)
//            }
//            Result.Success(listResult)
//
//        } catch (e: Exception) {
//            Logger.w("[${this::class.simpleName}] exception=${e.message}")
//            Result.Error(e)
//        }
//    }
}