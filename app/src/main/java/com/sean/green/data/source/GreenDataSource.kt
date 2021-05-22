package com.sean.green.data.source

import com.sean.green.data.Result
import com.sean.green.data.Save

/**
 *
 * Main entry point for accessing Stylish sources.
 */
interface GreenDataSource {

    suspend fun addSaveNum2Firebase(save: Save): Result<Boolean>

    suspend fun getSaveNum(): Result<List<Save>>

}