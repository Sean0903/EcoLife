package com.sean.green.data.source

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import com.sean.green.data.*

class FakeRepository(var data: MutableList<Save>? = mutableListOf()) : GreenRepository {

    override suspend fun postUser(user: User): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(userEmail: String, collection: String): Result<List<User>> {
        TODO("Not yet implemented")
    }

    override suspend fun firebaseAuthWithGoogle(account: GoogleSignInAccount?): Result<FirebaseUser?> {
        TODO("Not yet implemented")
    }

    override suspend fun addData2Firebase(
        userEmail: String,
        collection: String,
        any: Any
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun addArticle2Firebase(userEmail: String, article: Article): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getDataFromFirebase(
        userEmail: String,
        collection: String,
        any: Any
    ): Result<List<Any>> {
        TODO("Not yet implemented")
    }

    override suspend fun getChallengeNum(
        userEmail: String,
        collection: String
    ): Result<List<Challenge>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSaveNum(userEmail: String, collection: String): Result<List<Save>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUseNum(userEmail: String, collection: String): Result<List<Use>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCalendarEvent(
        userEmail: String,
        collection: String
    ): Result<List<CalendarEvent>> {
        TODO("Not yet implemented")
    }

    override suspend fun getArticle(userEmail: String, collection: String): Result<List<Article>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSaveDataForChart(
        userEmail: String,
        collection: String,
        documentId: String
    ): Result<List<Save>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUseDataForChart(
        userEmail: String,
        collection: String,
        documentId: String
    ): Result<List<Use>> {
        TODO("Not yet implemented")
    }

    override suspend fun addSharing2Firebase(collection: String, share: Share): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun addEvent2Firebase(collection: String, event: Event): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun addEventMember(
        eventId: String,
        userEmail: String,
        userImage: String
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun addEventInfo2UserFirebase(
        event: Event,
        eventId: String,
        eventDay: String,
        userEmail: String
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getSharingData(collection: String): Result<List<Share>> {
        TODO("Not yet implemented")
    }

    override suspend fun getEventData(collection: String): Result<List<Event>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSaveDataForShareChart(
        userEmail: String,
        share: Share,
        userDocumentId: String,
        collection: String,
        documentId: String
    ): Result<List<Save>> {
        TODO("Not yet implemented")
    }
}