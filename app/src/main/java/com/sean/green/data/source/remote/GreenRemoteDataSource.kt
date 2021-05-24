package com.sean.green.data.source.remote


import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.sean.green.GreenApplication
import com.sean.green.R
import com.sean.green.data.Challenge
import com.sean.green.data.FirebaseKey
import com.sean.green.data.Result
import com.sean.green.data.Save
import com.sean.green.data.source.GreenDataSource
import com.sean.green.util.Logger
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


object GreenRemoteDataSource : GreenDataSource {

    private const val PATH_GREEN = "green"
    private const val KEY_CREATED_TIME = "createdTime"

    override suspend fun getSaveNum(): Result<List<Save>> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_GREEN)
            .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Save>()
                    for (document in task.result!!) {
                        Log.d("seanGetSaveNum",document.id + " => " + document.data)

                        val saveNum = document.toObject(Save::class.java)
                        list.add(saveNum)
                    }

                    continuation.resume(Result.Success(list))

                } else {
                    task.exception?.let {

                        Log.w("sean","[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(GreenApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }

    override suspend fun getChallengeNum(): Result<List<Challenge>> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_GREEN)
            .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Challenge>()
                    for (document in task.result!!) {
                        Log.d("seanGetSaveNum",document.id + " => " + document.data)

                        val challengeNum = document.toObject(Challenge::class.java)
                        list.add(challengeNum)
                    }

                    continuation.resume(Result.Success(list))

                } else {
                    task.exception?.let {

                        Log.w("sean","[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(GreenApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }

    override suspend fun addSaveNum2Firebase(save: Save): Result<Boolean> = suspendCoroutine { continuation ->
        val saveNum = FirebaseFirestore.getInstance().collection(PATH_GREEN)
        val document =  saveNum.document()

        save.id = document.id
        save.createdTime = Calendar.getInstance().timeInMillis

        document
            .set(save)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("dataSource","addSaveNum2Firebase: $save")

                    continuation.resume(Result.Success(true))
                } else {
                    task.exception?.let {

                        Log.d("dataSource","[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(GreenApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }

    override suspend fun addChallenge2Firebase(challenge: Challenge): Result<Boolean> = suspendCoroutine { continuation ->
        val challengeNum = FirebaseFirestore.getInstance().collection(PATH_GREEN)
        val document =  challengeNum.document()

        challenge.id = document.id
        challenge.createdTime = Calendar.getInstance().timeInMillis

        document
            .set(challenge)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("dataSource","addChallenge2Firebase: $challenge")

                    continuation.resume(Result.Success(true))
                } else {
                    task.exception?.let {

                        Log.d("dataSource","[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(GreenApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }

}