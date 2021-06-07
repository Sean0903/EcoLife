package com.sean.green.data.source

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
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

    override suspend fun addSaveNum2Firebase(userEmail: String,save: Save): Result<Boolean> {
        return greenRemoteDataSource.addSaveNum2Firebase(userEmail,save)
    }

    override suspend fun addUseNum2Firebase(use: Use, userId: String): Result<Boolean> {
        return greenRemoteDataSource.addUseNum2Firebase(use, userId)
    }

    override suspend fun addChallenge2Firebase(challenge: Challenge, userId: String): Result<Boolean> {
        return greenRemoteDataSource.addChallenge2Firebase(challenge, userId)
    }

    override suspend fun getSaveNum( userEmail: String, collection: String): Result<List<Save>> {
        return greenRemoteDataSource.getSaveNum(userEmail,collection)
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

//    override suspend fun createUser(user: User): Result<Boolean> {
//        return  greenRemoteDataSource.createUser(user)
//    }
//
//    override suspend fun findUser(firebaseUserId: String): Result<User?> {
//        return greenRemoteDataSource.findUser(firebaseUserId)
//    }

    override suspend fun addSharing2Firebase(share: Share, userId: String): Result<Boolean> {
        return greenRemoteDataSource.addSharing2Firebase(share, userId)
    }

    override suspend fun getSharingData(collection: String, userId: String, documentId: String): Result<List<Share>> {
        return greenRemoteDataSource.getSharingData(userId, documentId)
    }

    override suspend fun postUser(user: User): Result<Boolean> {
        return greenRemoteDataSource.postUser(user)
    }

    override suspend fun firebaseAuthWithGoogle(account : GoogleSignInAccount?): Result<FirebaseUser?> {
        return greenRemoteDataSource.firebaseAuthWithGoogle(account)
    }

}