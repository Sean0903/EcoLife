package com.sean.green.data.source.local

import android.content.Context
import com.sean.green.data.Result
import com.sean.green.data.Save
import com.sean.green.data.source.GreenDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Concrete implementation of a Stylish source as a db.
 */
class GreenLocalDataSource(val context: Context) : GreenDataSource {

    override suspend fun addSaveNum2Firebase(save: Save): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getSaveNum(): Result<List<Save>> {
        TODO("Not yet implemented")
    }

//    override suspend fun getProductDetails(id: Long?): Result<DetailResult> {
//        TODO("Not yet implemented")
//    }
}