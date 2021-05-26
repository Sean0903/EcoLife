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

    suspend fun addUseNum2Firebase(use: Use): Result<Boolean>

    suspend fun addChallenge2Firebase(challenge: Challenge): Result<Boolean>

    suspend fun getSaveNum(): Result<List<Save>>

    suspend fun getChallengeNum(): Result<List<Challenge>>

    suspend fun getUseNum(): Result<List<Use>>



}