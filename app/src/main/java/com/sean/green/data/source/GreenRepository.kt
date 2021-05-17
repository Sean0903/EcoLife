package com.sean.green.data.source

/**
 *
 * Interface to the Environment layers.
 */

interface GreenRepository {

    suspend fun getObject(collection: String):List<Any>

}