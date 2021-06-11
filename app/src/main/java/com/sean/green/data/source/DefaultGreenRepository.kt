package com.sean.green.data.source

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import com.sean.green.data.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

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

    override suspend fun addUseNum2Firebase(userEmail: String, use: Use): Result<Boolean> {
        return greenRemoteDataSource.addUseNum2Firebase(userEmail,use)
    }

    override suspend fun addChallenge2Firebase(userEmail: String,challenge: Challenge): Result<Boolean> {
        return greenRemoteDataSource.addChallenge2Firebase(userEmail,challenge)
    }

    override suspend fun getSaveNum( userEmail: String, collection: String): Result<List<Save>> {
        return greenRemoteDataSource.getSaveNum(userEmail,collection)
    }

    override suspend fun getChallengeNum(userEmail: String,collection: String): Result<List<Challenge>> {
        return greenRemoteDataSource.getChallengeNum(userEmail,collection)
    }

    override suspend fun getUseNum(userEmail: String,collection: String): Result<List<Use>> {
        return greenRemoteDataSource.getUseNum(userEmail,collection)
    }

    override suspend fun getCalendarEvent(userEmail: String, collection: String): Result<List<CalendarEvent>> {
        return greenRemoteDataSource.getCalendarEvent(userEmail,collection)
    }

    override suspend fun getSaveDataForChart(userEmail: String, collection: String, documentId: String): Result<List<Save>> {
        return greenRemoteDataSource.getSaveDataForChart(userEmail,collection,documentId)
    }

    override suspend fun getUseDataForChart(userEmail: String, collection: String, documentId: String): Result<List<Use>> {
        return greenRemoteDataSource.getUseDataForChart(userEmail,collection,documentId)
    }

    override suspend fun addSharing2Firebase(collection: String, share: Share): Result<Boolean> {
        return greenRemoteDataSource.addSharing2Firebase(collection,share)
    }

    override suspend fun getSharingData(collection: String): Result<List<Share>> {
        return greenRemoteDataSource.getSharingData(collection)
    }

    override suspend fun postUser(user: User): Result<Boolean> {
        return greenRemoteDataSource.postUser(user)
    }

    override suspend fun getUser(userEmail: String,collection: String): Result<List<User>> {
        return greenRemoteDataSource.getUser(userEmail,collection)
    }

    override suspend fun firebaseAuthWithGoogle(account : GoogleSignInAccount?): Result<FirebaseUser?> {
        return greenRemoteDataSource.firebaseAuthWithGoogle(account)
    }

    override suspend fun addEvent2Firebase(collection: String, event: Event): Result<Boolean> {
        return greenRemoteDataSource.addEvent2Firebase(collection,event)
    }

    override suspend fun getEventData(collection: String): Result<List<Event>> {
        return greenRemoteDataSource.getEventData(collection)
    }

    override suspend fun addEventMember(eventId: String, userEmail: String, userImage: String): Result<Boolean> {
        return greenRemoteDataSource.addEventMember(eventId,userEmail,userImage)
    }

}