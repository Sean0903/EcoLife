package com.sean.green.data.source

import com.sean.green.data.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.sql.Timestamp

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Concrete implementation to load Stylish sources.
 */
class DefaultGreenRepository(
    private val greenRemoteDataSource: GreenDataSource,
    private val greenLocalDataSource: GreenDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GreenRepository {

    override suspend fun addSaveNum2Firebase(save: Save, userId: String): Result<Boolean> {
        return greenRemoteDataSource.addSaveNum2Firebase(save, userId)
    }

    override suspend fun addUseNum2Firebase(use: Use, userId: String): Result<Boolean> {
        return greenRemoteDataSource.addUseNum2Firebase(use, userId)
    }

    override suspend fun addChallenge2Firebase(challenge: Challenge, userId: String): Result<Boolean> {
        return greenRemoteDataSource.addChallenge2Firebase(challenge, userId)
    }

    override suspend fun getSaveNum(collection: String, userId: String): Result<List<Save>> {
        return greenRemoteDataSource.getSaveNum(userId)
    }

    override suspend fun getChallengeNum(collection: String, userId: String): Result<List<Challenge>> {
        return greenRemoteDataSource.getChallengeNum(userId)
    }

    override suspend fun getUseNum(collection: String, userId: String): Result<List<Use>> {
        return greenRemoteDataSource.getUseNum(userId)
    }

    override suspend fun getCalendarEvent(collection: String, userId: String): Result<List<CalendarEvent>> {
        return greenRemoteDataSource.getCalendarEvent(userId)
    }

    override suspend fun getSaveDataForChart(collection: String, userId: String,documentId: String): Result<List<Save>> {
        return greenRemoteDataSource.getSaveDataForChart(userId,documentId)
    }

    override suspend fun getUseDataForChart(collection: String, userId: String,documentId: String): Result<List<Use>> {
        return greenRemoteDataSource.getUseDataForChart(userId,documentId)
    }

    override suspend fun createUser(user: User): Result<Boolean> {
        return  greenRemoteDataSource.createUser(user)
    }

    override suspend fun findUser(firebaseUserId: String): Result<User?> {
        return greenRemoteDataSource.findUser(firebaseUserId)
    }


}