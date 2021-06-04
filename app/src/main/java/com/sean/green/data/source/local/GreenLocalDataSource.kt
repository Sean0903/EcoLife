package com.sean.green.data.source.local

import android.content.Context
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

    override suspend fun addSaveNum2Firebase(save: Save, userId: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun addUseNum2Firebase(use: Use, userId: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun addChallenge2Firebase(
        challenge: Challenge,
        userId: String
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getSaveNum(userId: String): Result<List<Save>> {
        TODO("Not yet implemented")
    }

    override suspend fun getChallengeNum(userId: String): Result<List<Challenge>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUseNum(userId: String): Result<List<Use>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCalendarEvent(userId: String): Result<List<CalendarEvent>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSaveDataForChart(userId: String, documentId: String): Result<List<Save>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUseDataForChart(userId: String, documentId: String): Result<List<Use>> {
        TODO("Not yet implemented")
    }
}