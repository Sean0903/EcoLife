package com.sean.green.data.source

import com.sean.green.data.Challenge
import com.sean.green.data.Result
import com.sean.green.data.Save
import com.sean.green.data.Use

/**
 *
 * Main entry point for accessing Stylish sources.
 */
interface GreenDataSource {

    suspend fun addSaveNum2Firebase(save: Save,userId: String): Result<Boolean>

    suspend fun addUseNum2Firebase(use: Use, userId: String): Result<Boolean>

    suspend fun addChallenge2Firebase(challenge: Challenge,userId: String): Result<Boolean>

    suspend fun getSaveNum(userId: String): Result<List<Save>>

    suspend fun getChallengeNum(userId: String): Result<List<Challenge>>

    suspend fun getUseNum(userId: String): Result<List<Use>>



}