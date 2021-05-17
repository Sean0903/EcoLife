package com.sean.green.data.source.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.sean.green.data.FirebaseKey
import com.sean.green.data.source.GreenDataSource
import java.sql.Timestamp

object GreenRemoteDataSource : GreenDataSource {

//    override suspend fun getObjects(
//        collection: String,
//        start: Timestamp,
//        end: Timestamp
//    ): List<Any> {

//        val listFromFirebase = mutableListOf<Any>()
//
//        when (collection) {
//
//            FirebaseKey.COLLECTION_SAVE -> {
//
//                UserManager.USER_REFERENCE?.let {
//
//                    for (shape in it.collection(collection)
//                        .orderBy(FirebaseKey.TIMESTAMP, Query.Direction.DESCENDING)
//                        .whereGreaterThanOrEqualTo(FirebaseKey.TIMESTAMP, start)
//                        .whereLessThanOrEqualTo(FirebaseKey.TIMESTAMP, end)
//                        .get().await()) {
//
//                        listFromFirebase.add(shape.toObject(Shape::class.java))
//                        (listFromFirebase[listFromFirebase.lastIndex] as Shape).docId =
//                            shape.id
//                    }
//
//                }
//            }
//        }
//        Logger.i("getListFromFirebase = $collection -> $listFromFirebase")
//        return listFromFirebase
//    }
}