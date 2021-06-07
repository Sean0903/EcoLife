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

    suspend fun addUseNum2Firebase(use: Use, userId: String): Result<Boolean>

    suspend fun addChallenge2Firebase(challenge: Challenge, userId: String): Result<Boolean>

    suspend fun getSaveNum(userEmail: String,collection:String): Result<List<Save>>

    suspend fun getChallengeNum(userId: String): Result<List<Challenge>>

    suspend fun getUseNum(userId: String): Result<List<Use>>

    suspend fun getCalendarEvent(userId: String): Result<List<CalendarEvent>>

    suspend fun getSaveDataForChart(userId: String,documentId: String): Result<List<Save>>

    suspend fun getUseDataForChart(userId: String,documentId: String): Result<List<Use>>

//    suspend fun createUser(user: User): Result<Boolean>
//
//    suspend fun findUser(firebaseUserId: String): Result<User?>

    suspend fun addSharing2Firebase(share: Share, userId: String): Result<Boolean>

    suspend fun getSharingData(userId: String,documentId: String): Result<List<Share>>

    suspend fun postUser(user: User): Result<Boolean>

    suspend fun firebaseAuthWithGoogle(account : GoogleSignInAccount?): Result<FirebaseUser?>
}