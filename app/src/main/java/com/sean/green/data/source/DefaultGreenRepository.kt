package com.sean.green.data.source

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Concrete implementation to load Stylish sources.
 */
class DefaultGreenRepository(private val greenRemoteDataSource: GreenDataSource,
                               private val greenLocalDataSource: GreenDataSource,
                               private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GreenRepository {
    override suspend fun getObject(collection: String): List<Any> {
        TODO("Not yet implemented")
    }

//    override suspend fun getProductDetails(id: Long?):Result<DetailResult> {
//        return stylishRemoteDataSource.getProductDetails(id = id)
//    }
}