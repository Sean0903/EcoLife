package com.sean.green.data.source.local

import android.content.Context
import com.sean.green.data.Challenge
import com.sean.green.data.Result
import com.sean.green.data.Save
import com.sean.green.data.Use
import com.sean.green.data.source.GreenDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Concrete implementation of a Stylish source as a db.
 */
class GreenLocalDataSource(val context: Context) : GreenDataSource {

    override suspend fun addSaveNum2Firebase(save: Save,userId: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun addUseNum2Firebase(use: Use): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun addChallenge2Firebase(challenge: Challenge): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getSaveNum(): Result<List<Save>> {
        TODO("Not yet implemented")
    }

    override suspend fun getChallengeNum(): Result<List<Challenge>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUseNum(): Result<List<Use>> {
        TODO("Not yet implemented")
    }
}