package com.sean.green.data.source

import com.sean.green.data.Challenge
import com.sean.green.data.Result
import com.sean.green.data.Save
import com.sean.green.data.Use
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.sql.Timestamp

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Concrete implementation to load Stylish sources.
 */
class DefaultGreenRepository(private val greenRemoteDataSource: GreenDataSource,
                               private val greenLocalDataSource: GreenDataSource,
                               private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GreenRepository {

    override suspend fun addSaveNum2Firebase(save: Save,userId: String): Result<Boolean> {
        return greenRemoteDataSource.addSaveNum2Firebase(save,userId)
    }

    override suspend fun addUseNum2Firebase(use: Use): Result<Boolean> {
        return greenRemoteDataSource.addUseNum2Firebase(use)
    }

    override suspend fun addChallenge2Firebase(challenge: Challenge): Result<Boolean> {
        return greenRemoteDataSource.addChallenge2Firebase(challenge)
    }

    override suspend fun getSaveNum(collection: String): Result<List<Save>> {
        return greenRemoteDataSource.getSaveNum()
    }

    override suspend fun getChallengeNum(collection: String): Result<List<Challenge>> {
        return greenRemoteDataSource.getChallengeNum()
    }

    override suspend fun getUseNum(collection: String): Result<List<Use>> {
        return greenRemoteDataSource.getUseNum()
    }


//    override suspend fun getObjects(collection: String, start: Timestamp, end: Timestamp): List<Any> {
//        return greenRemoteDataSource.getObjects(collection= collection)
//    }

//    override suspend fun getProductDetails(id: Long?):Result<DetailResult> {
//        return stylishRemoteDataSource.getProductDetails(id = id)
//    }
}