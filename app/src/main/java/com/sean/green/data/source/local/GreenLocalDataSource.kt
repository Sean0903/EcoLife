package com.sean.green.data.source.local

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import com.sean.green.data.*
import com.sean.green.data.source.GreenDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Concrete implementation of a Stylish source as a db.
 */
class GreenLocalDataSource(val context: Context) : GreenDataSource {

    override suspend fun addSaveNum2Firebase(userEmail: String,save: Save): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun addUseNum2Firebase(userEmail: String,use: Use): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun addChallenge2Firebase(userEmail: String,challenge: Challenge): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getSaveNum(userEmail: String,collection: String): Result<List<Save>> {
        TODO("Not yet implemented")
    }

    override suspend fun getChallengeNum(userEmail: String,collection: String): Result<List<Challenge>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUseNum(userEmail: String,collection: String): Result<List<Use>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCalendarEvent(userEmail: String,collection: String): Result<List<CalendarEvent>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSaveDataForChart(userEmail: String,collection: String, documentId: String): Result<List<Save>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUseDataForChart(userEmail: String, collection: String, documentId: String): Result<List<Use>> {
        TODO("Not yet implemented")
    }

//    override suspend fun createUser(user: User): Result<Boolean> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun findUser(firebaseUserId: String): Result<User?> {
//        TODO("Not yet implemented")
//    }

    override suspend fun addSharing2Firebase(collection: String, share: Share): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getSharingData(collection: String): Result<List<Share>> {
        TODO("Not yet implemented")
    }

    override suspend fun postUser(user: User): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(userEmail: String,collection: String): Result<List<User>> {
        TODO("Not yet implemented")
    }

    override suspend fun firebaseAuthWithGoogle(account: GoogleSignInAccount?): Result<FirebaseUser?> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun addEvent2Firebase(collection: String, event: Event): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getEventData(collection: String): Result<List<Event>> {
        TODO("Not yet implemented")
    }

    override suspend fun addEventMember(eventId: String, userEmail: String, userImage: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

}