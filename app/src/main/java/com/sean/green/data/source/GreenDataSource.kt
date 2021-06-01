package com.sean.green.data.source

import com.sean.green.data.*

/**
 *
 * Main entry point for accessing Stylish sources.
 */
interface GreenDataSource {

    suspend fun addSaveNum2Firebase(save: Save, userId: String): Result<Boolean>

    suspend fun addUseNum2Firebase(use: Use, userId: String): Result<Boolean>

    suspend fun addChallenge2Firebase(challenge: Challenge, userId: String): Result<Boolean>

    suspend fun getSaveNum(userId: String): Result<List<Save>>

    suspend fun getChallengeNum(userId: String): Result<List<Challenge>>

    suspend fun getUseNum(userId: String): Result<List<Use>>

    suspend fun getCalendarEvent(userId: String): Result<List<CalendarEvent>>

    suspend fun getSaveDataForChart(userId: String,documentId: String): Result<List<Save>>

}