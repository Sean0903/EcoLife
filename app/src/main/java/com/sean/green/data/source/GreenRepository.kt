package com.sean.green.data.source

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import com.sean.green.data.*
import java.sql.Timestamp

/**
 *
 * Interface to the Environment layers.
 */

interface GreenRepository {

    suspend fun addChallenge2Firebase(challenge: Challenge, userId: String): Result<Boolean>

    suspend fun addSaveNum2Firebase(userEmail: String,save: Save): Result<Boolean>

    suspend fun addUseNum2Firebase(use: Use, userId: String): Result<Boolean>

    suspend fun getChallengeNum(collection: String, userId: String): Result<List<Challenge>>

    suspend fun getSaveNum(userEmail: String,collection: String): Result<List<Save>>

    suspend fun getUseNum(collection: String, userId: String): Result<List<Use>>

    suspend fun getCalendarEvent(collection: String, userId: String): Result<List<CalendarEvent>>

    suspend fun getSaveDataForChart(collection: String, userId: String,documentId: String): Result<List<Save>>

    suspend fun getUseDataForChart(collection: String, userId: String,documentId: String): Result<List<Use>>

//    suspend fun createUser(user: User): Result<Boolean>
//
//    suspend fun findUser(firebaseUserId: String): Result<User?>

    suspend fun addSharing2Firebase(share: Share, userId: String): Result<Boolean>

    suspend fun getSharingData(collection: String, userId: String,documentId: String): Result<List<Share>>

    suspend fun postUser(user: User): Result<Boolean>

    suspend fun firebaseAuthWithGoogle(account : GoogleSignInAccount?): Result<FirebaseUser?>
}