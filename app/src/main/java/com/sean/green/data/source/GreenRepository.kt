package com.sean.green.data.source

import com.sean.green.data.Result
import com.sean.green.data.Save

/**
 *
 * Interface to the Environment layers.
 */

interface GreenRepository {

    suspend fun getObjects(): Result<List<Save>>

}