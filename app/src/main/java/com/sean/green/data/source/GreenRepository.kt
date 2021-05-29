package com.sean.green.data.source

import com.sean.green.data.*
import java.sql.Timestamp

/**
 *
 * Interface to the Environment layers.
 */

interface GreenRepository {

    suspend fun addChallenge2Firebase(challenge: Challenge, userId: String): Result<Boolean>

    suspend fun addSaveNum2Firebase(save: Save, userId: String): Result<Boolean>

    suspend fun addUseNum2Firebase(use: Use, userId: String): Result<Boolean>

    suspend fun getChallengeNum(collection: String, userId: String): Result<List<Challenge>>

    suspend fun getSaveNum(collection: String, userId: String): Result<List<Save>>

    suspend fun getUseNum(collection: String, userId: String): Result<List<Use>>

    suspend fun getCalendarEvent(collection: String, userId: String): Result<List<CalendarEvent>>
}