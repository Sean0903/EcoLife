package com.sean.green.data.source

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import com.sean.green.data.*

interface GreenRepository {

    suspend fun postUser(user: User): Result<Boolean>

    suspend fun getUser(userEmail: String,collection: String): Result<List<User>>

    suspend fun firebaseAuthWithGoogle(account: GoogleSignInAccount?): Result<FirebaseUser?>

    suspend fun addData2Firebase(userEmail: String, collection: String, any: Any): Result<Boolean>

    suspend fun addArticle2Firebase(userEmail: String, article: Article): Result<Boolean>

    suspend fun getDataFromFirebase(userEmail: String, collection: String, any: Any): Result<List<Any>>

    suspend fun getChallengeNum(userEmail: String, collection: String): Result<List<Challenge>>

    suspend fun getSaveNum(userEmail: String, collection: String): Result<List<Save>>

    suspend fun getUseNum(userEmail: String, collection: String): Result<List<Use>>

    suspend fun getCalendarEvent(userEmail: String, collection: String): Result<List<CalendarEvent>>

    suspend fun getArticle(userEmail: String, collection: String): Result<List<Article>>

    suspend fun getSaveDataForChart(userEmail: String, collection: String, documentId: String): Result<List<Save>>

    suspend fun getUseDataForChart(userEmail: String, collection: String, documentId: String): Result<List<Use>>

    suspend fun addSharing2Firebase(collection: String, share: Share): Result<Boolean>

    suspend fun addEvent2Firebase(collection: String, event: Event): Result<Boolean>

    suspend fun addEventMember(eventId: String, userEmail: String, userImage: String): Result<Boolean>

    suspend fun addEventInfo2UserFirebase(event: Event,eventId: String, eventDay: String,userEmail: String): Result<Boolean>

    suspend fun getSharingData(collection: String): Result<List<Share>>

    suspend fun getEventData(collection: String): Result<List<Event>>

    suspend fun getSaveDataForShareChart(userEmail: String,share: Share,userDocumentId: String,collection: String, documentId: String): Result<List<Save>>

}