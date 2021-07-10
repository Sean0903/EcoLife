package com.sean.green.data.source

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import com.sean.green.data.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DefaultGreenRepository(
    private val greenRemoteDataSource: GreenDataSource,
    private val greenLocalDataSource: GreenDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GreenRepository {

    override suspend fun addData2Firebase(userEmail: String, collection: String, any: Any): Result<Boolean> {
        return greenRemoteDataSource.addData2Firebase(userEmail,collection,any)
    }

    override suspend fun getDataFromFirebase(userEmail: String, collection: String, any: Any): Result<List<Any>> {
        return greenRemoteDataSource.getDataFromFirebase(userEmail,collection,any)
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

    override suspend fun addArticle2Firebase(userEmail: String, article: Article): Result<Boolean> {
        return greenRemoteDataSource.addArticle2Firebase(userEmail,article)
    }

    override suspend fun getArticle(userEmail: String, collection: String): Result<List<Article>> {
        return greenRemoteDataSource.getArticle(userEmail,collection)
    }

    override suspend fun addEventInfo2UserFirebase(event: Event,eventId: String, eventDay: String,userEmail: String): Result<Boolean> {
        return greenRemoteDataSource.addEventInfo2UserFirebase(event,eventId,eventDay,userEmail)
    }

    override suspend fun getSaveDataForShareChart(userEmail: String,share: Share,userDocumentId: String,collection: String, documentId: String): Result<List<Save>> {
        return greenRemoteDataSource.getSaveDataForShareChart(userEmail,share,userDocumentId,collection,documentId)
    }

}