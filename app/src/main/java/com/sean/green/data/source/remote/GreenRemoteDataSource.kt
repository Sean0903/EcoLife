import android.provider.DocumentsContract
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.sean.green.GreenApplication
import com.sean.green.R
import com.sean.green.data.*
import com.sean.green.data.source.GreenDataSource
import com.sean.green.ext.toDisplayFormat
import com.sean.green.ext.toDisplayFormatDay
import com.sean.green.ext.toDisplayFormatMonth
import com.sean.green.ext.toDisplayFormatYear
import com.sean.green.util.Logger
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


object GreenRemoteDataSource : GreenDataSource {

    private const val PATH_GREEN = "green"
    private const val PATH_USERS = "users"
    private const val PATH_GREENS = "greens"
    private const val KEY_CREATED_TIME = "createdTime"


    override suspend fun getSaveDataForChart(userId: String,documentId: String): Result<List<Save>> =
        suspendCoroutine { continuation ->

            val today = Calendar.getInstance().timeInMillis.toDisplayFormat()
//            val oneWeek =

            val firestore = FirebaseFirestore.getInstance().collection(PATH_USERS)
            val userDocument = firestore.document(userId)
            val greensCollenction = userDocument.collection("greens")
            val dayDocument = greensCollenction.document(documentId)
            val dayCollection = dayDocument.collection("save")

                    dayCollection
                .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<Save>()
                        for (document in task.result!!) {
                            Log.d("getSaveDataForChart", document.id + " => " + document.data)

                            val saveNum = document.toObject(Save::class.java)
                            list.add(saveNum)
                        }

                        continuation.resume(Result.Success(list))

                    } else {
                        task.exception?.let {

                            Log.w(
                                "sean",
                                "[${this::class.simpleName}] Error getting documents. ${it.message}"
                            )
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(GreenApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }


    override suspend fun getUseDataForChart(userId: String,documentId: String): Result<List<Use>> =
        suspendCoroutine { continuation ->

            val today = Calendar.getInstance().timeInMillis.toDisplayFormat()

            val firestore = FirebaseFirestore.getInstance().collection(PATH_USERS)
            val userDocument = firestore.document(userId)
            val greensCollenction = userDocument.collection("greens")
            val dayDocument = greensCollenction.document(documentId)
            val dayCollection = dayDocument.collection("use")

            dayCollection
                .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<Use>()
                        for (document in task.result!!) {
                            Log.d("getUseDataForChart", document.id + " => " + document.data)

                            val useNum = document.toObject(Use::class.java)
                            list.add(useNum)
                        }

                        continuation.resume(Result.Success(list))

                    } else {
                        task.exception?.let {

                            Log.w(
                                "sean",
                                "[${this::class.simpleName}] Error getting documents. ${it.message}"
                            )
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(GreenApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }



    override suspend fun getSaveNum(userId: String): Result<List<Save>> =
        suspendCoroutine { continuation ->

            val today = Calendar.getInstance().timeInMillis.toDisplayFormat()

            FirebaseFirestore.getInstance().collection(PATH_USERS).document(userId)
                .collection("greens").document(
                    today
                ).collection("save")
                .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<Save>()
                        for (document in task.result!!) {
                            Log.d("seanGetSaveNum", document.id + " => " + document.data)

                            val saveNum = document.toObject(Save::class.java)
                            list.add(saveNum)
                        }

                        continuation.resume(Result.Success(list))

                    } else {
                        task.exception?.let {

                            Log.w(
                                "sean",
                                "[${this::class.simpleName}] Error getting documents. ${it.message}"
                            )
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(GreenApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }

    override suspend fun getUseNum(userId: String): Result<List<Use>> =
        suspendCoroutine { continuation ->

            val today = Calendar.getInstance().timeInMillis.toDisplayFormat()

            FirebaseFirestore.getInstance()
                .collection(PATH_USERS).document(userId).collection("greens").document(
                    today
                ).collection("use")
                .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<Use>()
                        for (document in task.result!!) {
                            Log.d("seanGetUseNum", document.id + " => " + document.data)

                            val useNum = document.toObject(Use::class.java)
                            list.add(useNum)
                        }

                        continuation.resume(Result.Success(list))

                    } else {
                        task.exception?.let {

                            Log.w(
                                "sean",
                                "[${this::class.simpleName}] Error getting documents. ${it.message}"
                            )
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(GreenApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }

    override suspend fun getChallengeNum(userId: String): Result<List<Challenge>> =
        suspendCoroutine { continuation ->

            val today = Calendar.getInstance().timeInMillis.toDisplayFormat()

            FirebaseFirestore.getInstance()
                .collection(PATH_USERS).document(userId).collection("greens").document(
                    today
                ).collection("challenge")
                .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<Challenge>()
                        for (document in task.result!!) {
                            Log.d("seanGetChallengeNum", document.id + " => " + document.data)

                            val challengeNum = document.toObject(Challenge::class.java)
                            list.add(challengeNum)
                        }

                        continuation.resume(Result.Success(list))

                    } else {
                        task.exception?.let {

                            Log.w(
                                "sean",
                                "[${this::class.simpleName}] Error getting documents. ${it.message}"
                            )
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(GreenApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }


    override suspend fun getCalendarEvent(userId: String): Result<List<CalendarEvent>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(PATH_USERS).document(userId).collection("greens")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<CalendarEvent>()
                        for (document in task.result!!) {
                            Log.d("seanGetCalendarEvent", document.id + " => " + document.data)

                            val calendarEvent = document.toObject(CalendarEvent::class.java)
                            list.add(calendarEvent)
                        }

                        continuation.resume(Result.Success(list))

                    } else {
                        task.exception?.let {

                            Log.w(
                                "sean",
                                "[${this::class.simpleName}] Error getting documents. ${it.message}"
                            )
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(GreenApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }



    override suspend fun addSaveNum2Firebase(save: Save, userId: String): Result<Boolean> =
        suspendCoroutine { continuation ->

            val today = Calendar.getInstance().timeInMillis.toDisplayFormat()

            val firestore = FirebaseFirestore.getInstance().collection(PATH_USERS)
            val userDocument = firestore.document(userId)
            val greensCollenction = userDocument.collection("greens")
            val todayDocument = greensCollenction.document(today)
            val saveCollection = todayDocument.collection("save").document()

            save.id = saveCollection.id
            saveCollection.set(save)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("dataSource", "addSaveNum2Firebase: $save")
                        continuation.resume(Result.Success(true))
                    } else {
                        task.exception?.let {

                            Log.d(
                                "dataSource",
                                "[${this::class.simpleName}] Error getting documents. ${it.message}"
                            )
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(GreenApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }

    override suspend fun addUseNum2Firebase(use: Use, userId: String): Result<Boolean> =
        suspendCoroutine { continuation ->

            val today = Calendar.getInstance().timeInMillis.toDisplayFormat()
//            val year = Calendar.getInstance().timeInMillis.toDisplayFormatYear()
//            val month = Calendar.getInstance().timeInMillis.toDisplayFormatMonth()
//            val day = Calendar.getInstance().timeInMillis.toDisplayFormatDay()
//            val createdTime = Calendar.getInstance().timeInMillis

            val firestore = FirebaseFirestore.getInstance().collection(PATH_USERS)
            val userDocument = firestore.document(userId)
            val greensCollenction = userDocument.collection("greens")
            val todayDocument = greensCollenction.document(today)
            val useCollection = todayDocument.collection("use").document()

            useCollection
                .set(use)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("dataSource", "addUseNum2Firebase: $use")

                        continuation.resume(Result.Success(true))
                    } else {
                        task.exception?.let {

                            Log.d(
                                "dataSource",
                                "[${this::class.simpleName}] Error getting documents. ${it.message}"
                            )
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(GreenApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }

//            greensCollenction
//                .whereEqualTo("year", year)
//                .whereEqualTo("month", month)
//                .whereEqualTo("day", day)
//                .get().addOnCompleteListener {
//                    Log.d("sean0531", "greensCollenction.whereEqualTo")
//
//                    Log.d("seam0531", "it =${it.result!!} ")
//
//                    if (it.isSuccessful) {
//
//                        if (it.result!!.isEmpty) {
//                            todayDocument.set(useTimeData)
//                            use.id = useCollection.id
//                            sendData2Firebase
//
//                        } else {
//                            for (document in it.result!!) {
//                                todayDocument.set(useTimeData, SetOptions.merge())
//                                use.id = useCollection.id
//                                sendData2Firebase
//                                Log.d("sean0531", "useTimeData, SetOptions.merge()")
//                            }
//                        }
//                    }
//                }
        }

    override suspend fun addChallenge2Firebase(
        challenge: Challenge,
        userId: String
    ): Result<Boolean> = suspendCoroutine { continuation ->

        val today = Calendar.getInstance().timeInMillis.toDisplayFormat()

        val firestore = FirebaseFirestore.getInstance().collection(PATH_USERS)
        val userDocument = firestore.document(userId)
        val greensCollenction = userDocument.collection("greens")
        val todayDocument = greensCollenction.document(today)
        val challengeCollection = todayDocument.collection("challenge").document()

        challenge.id = challengeCollection.id
        challengeCollection.set(challenge)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("dataSource", "addChallenge2Firebase: $challenge")

                    continuation.resume(Result.Success(true))
                } else {
                    task.exception?.let {

                        Log.d(
                            "dataSource",
                            "[${this::class.simpleName}] Error getting documents. ${it.message}"
                        )
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(GreenApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }
}