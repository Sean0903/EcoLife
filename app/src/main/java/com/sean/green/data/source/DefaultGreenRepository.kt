package com.sean.green.data.source

import com.sean.green.data.Result
import com.sean.green.data.Save
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

    override suspend fun addSaveNum2Firebase(save: Save): Result<Boolean> {
        return greenRemoteDataSource.addSaveNum2Firebase(save)
    }

    override suspend fun getSaveNum(collectionSave: String): Result<List<Save>> {
        return greenRemoteDataSource.getSaveNum()
    }
//    override suspend fun getObjects(collection: String, start: Timestamp, end: Timestamp): List<Any> {
//        return greenRemoteDataSource.getObjects(collection= collection)
//    }

//    override suspend fun getProductDetails(id: Long?):Result<DetailResult> {
//        return stylishRemoteDataSource.getProductDetails(id = id)
//    }
}