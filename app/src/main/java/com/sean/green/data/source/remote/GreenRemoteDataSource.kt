package com.sean.green.data.source.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.sean.green.GreenApplication
import com.sean.green.R
import com.sean.green.data.FirebaseKey
import com.sean.green.data.Result
import com.sean.green.data.Save
import com.sean.green.data.source.GreenDataSource
import com.sean.green.util.Logger
import java.sql.Timestamp
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


object GreenRemoteDataSource : GreenDataSource {

    private const val PATH_GREEN = "green"
    private const val KEY_CREATED_TIME = "createdTime"

    override suspend fun getObjects(): Result<List<Save>> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_GREEN)
            .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Save>()
                    for (document in task.result!!) {
//                        Logger.d(document.id + " => " + document.data)

                        val article = document.toObject(Save::class.java)
                        list.add(article)
                    }
//                    continuation.resume(Result.Success(list))
                } else {
                    task.exception?.let {

//                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
//                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
//                    continuation.resume(Result.Fail(GreenApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }

}