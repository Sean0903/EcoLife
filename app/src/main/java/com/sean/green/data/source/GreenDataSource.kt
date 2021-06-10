package com.sean.green.data.source

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import com.sean.green.data.*

/**
 *
 * Main entry point for accessing Stylish sources.
 */
interface GreenDataSource {

    suspend fun addSaveNum2Firebase(userEmail: String,save: Save): Result<Boolean>

    suspend fun addUseNum2Firebase(userEmail: String, use: Use): Result<Boolean>

    suspend fun addChallenge2Firebase(userEmail: String,challenge: Challenge): Result<Boolean>

    suspend fun getSaveNum(userEmail: String,collection:String): Result<List<Save>>

    suspend fun getChallengeNum(userEmail: String,collection:String): Result<List<Challenge>>

    suspend fun getUseNum(userEmail: String,collection: String): Result<List<Use>>

    suspend fun getCalendarEvent(userEmail: String, collection: String): Result<List<CalendarEvent>>

    suspend fun getSaveDataForChart(userEmail: String, collection: String, documentId: String): Result<List<Save>>

    suspend fun getUseDataForChart(userEmail: String, collection: String, documentId: String): Result<List<Use>>

    suspend fun addSharing2Firebase(collection: String, share: Share): Result<Boolean>

    suspend fun getSharingData(collection: String): Result<List<Share>>

    suspend fun postUser(user: User): Result<Boolean>

    suspend fun getUser(userEmail: String,collection: String): Result<List<User>>

    suspend fun firebaseAuthWithGoogle(account : GoogleSignInAccount?): Result<FirebaseUser?>

    suspend fun addEvent2Firebase(collection: String, event: Event): Result<Boolean>

    suspend fun getEventData(collection: String): Result<List<Event>>
}