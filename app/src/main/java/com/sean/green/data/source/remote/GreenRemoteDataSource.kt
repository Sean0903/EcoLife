
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.sean.green.GreenApplication
import com.sean.green.R
import com.sean.green.data.*
import com.sean.green.data.source.GreenDataSource
import com.sean.green.ext.toDisplayFormat
import com.sean.green.login.UserManager
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


object GreenRemoteDataSource : GreenDataSource {

    private const val PATH_USERS = "users"
    private const val PATH_SHARE = "share"
    private const val PATH_EVENT = "event"
    private const val PATH_GREENS = "greens"
    private const val KEY_CREATED_TIME = "createdTime"
    private const val KEY_EVENT_MEMBER = "member"
    private const val KEY_EVENT_MEMBER_IMAGE = "memberImage"


    override suspend fun getSaveDataForChart(
        userEmail: String,
        collection: String,
        documentId: String
    ): Result<List<Save>> =
        suspendCoroutine { continuation ->

            val today = Calendar.getInstance().timeInMillis.toDisplayFormat()

            val firestore = FirebaseFirestore.getInstance().collection(PATH_USERS)
            val userDocument = firestore.document(userEmail)
            val greensCollenction = userDocument.collection(PATH_GREENS)
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


    override suspend fun getUseDataForChart(
        userEmail: String,
        collection: String,
        documentId: String
    ): Result<List<Use>> =
        suspendCoroutine { continuation ->

            val today = Calendar.getInstance().timeInMillis.toDisplayFormat()

            val firestore = FirebaseFirestore.getInstance().collection(PATH_USERS)
            val userDocument = firestore.document(userEmail)
            val greensCollenction = userDocument.collection(PATH_GREENS)
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

    override suspend fun getSaveNum(userEmail: String, collection: String): Result<List<Save>> =
        suspendCoroutine { continuation ->

            val today = Calendar.getInstance().timeInMillis.toDisplayFormat()

            FirebaseFirestore.getInstance().collection(PATH_USERS).document(userEmail)
                .collection(PATH_GREENS).document(
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

    override suspend fun getUseNum(userEmail: String, collection: String): Result<List<Use>> =
        suspendCoroutine { continuation ->

            val today = Calendar.getInstance().timeInMillis.toDisplayFormat()

            FirebaseFirestore.getInstance()
                .collection(PATH_USERS).document(userEmail).collection(PATH_GREENS).document(
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

    override suspend fun getChallengeNum(
        userEmail: String,
        collection: String
    ): Result<List<Challenge>> =
        suspendCoroutine { continuation ->

            val today = Calendar.getInstance().timeInMillis.toDisplayFormat()

            FirebaseFirestore.getInstance()
                .collection(PATH_USERS).document(userEmail).collection(PATH_GREENS).document(
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


    override suspend fun getCalendarEvent(
        userEmail: String,
        collection: String
    ): Result<List<CalendarEvent>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(PATH_USERS).document(userEmail).collection(PATH_GREENS)
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


    override suspend fun addSaveNum2Firebase(userEmail: String, save: Save): Result<Boolean> =
        suspendCoroutine { continuation ->

            val today = Calendar.getInstance().timeInMillis.toDisplayFormat()

            val firestore = FirebaseFirestore.getInstance().collection(PATH_USERS)
            val userDocument = firestore.document(userEmail)
            val greensCollenction = userDocument.collection(PATH_GREENS)
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

    override suspend fun addUseNum2Firebase(userEmail: String, use: Use): Result<Boolean> =
        suspendCoroutine { continuation ->

            val today = Calendar.getInstance().timeInMillis.toDisplayFormat()
//            val year = Calendar.getInstance().timeInMillis.toDisplayFormatYear()
//            val month = Calendar.getInstance().timeInMillis.toDisplayFormatMonth()
//            val day = Calendar.getInstance().timeInMillis.toDisplayFormatDay()
//            val createdTime = Calendar.getInstance().timeInMillis

            val firestore = FirebaseFirestore.getInstance().collection(PATH_USERS)
            val userDocument = firestore.document(userEmail)
            val greensCollenction = userDocument.collection(PATH_GREENS)
            val todayDocument = greensCollenction.document(today)
            val useCollection = todayDocument.collection("use").document()

            use.id = useCollection.id
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

    override suspend fun addChallenge2Firebase(userEmail: String, challenge: Challenge)
            : Result<Boolean> = suspendCoroutine { continuation ->

        val today = Calendar.getInstance().timeInMillis.toDisplayFormat()

        val firestore = FirebaseFirestore.getInstance().collection(PATH_USERS)
        val userDocument = firestore.document(userEmail)
        val greensCollenction = userDocument.collection(PATH_GREENS)
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

    override suspend fun addSharing2Firebase(collection: String, share: Share): Result<Boolean> =
        suspendCoroutine { continuation ->

            val today = Calendar.getInstance().timeInMillis.toDisplayFormat()

            val firestore = FirebaseFirestore.getInstance().collection(PATH_SHARE)
            val shareDocument = firestore.document()
//            val greensCollenction = userDocument.collection("greens")
//            val todayDocument = greensCollenction.document(today)
//            val saveCollection = todayDocument.collection("share").document()

            share.id = shareDocument.id
            shareDocument.set(share)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("dataSource", "addSharing2Firebase: $share")
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

    override suspend fun getSharingData(collection: String): Result<List<Share>> =
        suspendCoroutine { continuation ->

            val today = Calendar.getInstance().timeInMillis.toDisplayFormat()

            val firestore = FirebaseFirestore.getInstance().collection(PATH_SHARE)
//            val shareDocument = firestore.document()
//            val greensCollenction = userDocument.collection("greens")
//            val dayDocument = greensCollenction.document(documentId)
//            val dayCollection = dayDocument.collection("share")

            firestore
                .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<Share>()
                        for (document in task.result!!) {
                            Log.d("getUseDataForChart", document.id + " => " + document.data)

                            val shareData = document.toObject(Share::class.java)
                            list.add(shareData)
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

    override suspend fun postUser(user: User): Result<Boolean> = suspendCoroutine { continuation ->

        val users = FirebaseFirestore.getInstance().collection(PATH_USERS)
        val document = users.document(user.email)
        user.userId = document.id

        users.whereEqualTo("email", user.email)
            .get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    document
                        .set(user)
                        .addOnSuccessListener {
                            Log.d("postUser", "DocumentSnapshot added with ID: ${users}")
                        }
                        .addOnFailureListener { e ->
                            Log.w("postUser", "Error adding document $e")
                        }
                } else {
                    for (myDocument in result) {
                        Log.d("postUser,", "Already initialized")
                    }
                }
            }
    }

    override suspend fun getUser(userEmail: String, collection: String): Result<List<User>> =
        suspendCoroutine { continuation ->

            val firestore = FirebaseFirestore.getInstance().collection(PATH_USERS)


            firestore.whereEqualTo("email", userEmail)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<User>()
                        for (document in task.result!!) {
                            Log.d("seanGetChallengeNum", document.id + " => " + document.data)

                            val challengeNum = document.toObject(User::class.java)
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

    override suspend fun firebaseAuthWithGoogle(account: GoogleSignInAccount?): Result<FirebaseUser?> =
        suspendCoroutine { continuation ->
            val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
            val auth = FirebaseAuth.getInstance()

            auth?.signInWithCredential(credential)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.i("firebaseAuthWithGoogle", "Post: $credential")
                        task.result?.let {
                            continuation.resume(Result.Success(it.user))
                        }
                    } else {
                        task.exception?.let {

                            Log.w(
                                "firebaseAuthWithGoogle",
                                "[${this::class.simpleName}] Error getting documents."
                            )
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(GreenApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }

    override suspend fun addEvent2Firebase(collection: String, event: Event): Result<Boolean> =
        suspendCoroutine { continuation ->

            val today = Calendar.getInstance().timeInMillis.toDisplayFormat()

            val firestore = FirebaseFirestore.getInstance().collection(PATH_EVENT)
            val eventDocument = firestore.document()

            event.id = eventDocument.id
            eventDocument.set(event)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("dataSource", "addEvent2Firebase: $event")
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

    override suspend fun getEventData(collection: String): Result<List<Event>> =
        suspendCoroutine { continuation ->

            val today = Calendar.getInstance().timeInMillis.toDisplayFormat()

            val firestore = FirebaseFirestore.getInstance().collection(PATH_EVENT)
//            val shareDocument = firestore.document()
//            val greensCollenction = userDocument.collection("greens")
//            val dayDocument = greensCollenction.document(documentId)
//            val dayCollection = dayDocument.collection("share")

            firestore
                .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<Event>()
                        for (document in task.result!!) {
                            Log.d("getEventData", document.id + " => " + document.data)

                            val eventData = document.toObject(Event::class.java)
                            list.add(eventData)
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

    override suspend fun addEventMember(eventId: String, userEmail: String, userImage: String): Result<Boolean> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance().collection(PATH_EVENT).document(eventId)
                .update(
                    KEY_EVENT_MEMBER, FieldValue.arrayUnion(UserManager.user.email),
                    KEY_EVENT_MEMBER_IMAGE, FieldValue.arrayUnion(UserManager.user.image)
                )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("dataSource", "addEventMember: $task")
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

    override suspend fun addArticle2Firebase(userEmail: String, article: Article): Result<Boolean> =
        suspendCoroutine { continuation ->

            val today = Calendar.getInstance().timeInMillis.toDisplayFormat()

            val firestore = FirebaseFirestore.getInstance().collection(PATH_USERS)
            val userDocument = firestore.document(userEmail)
            val greensCollenction = userDocument.collection(PATH_GREENS)
            val todayDocument = greensCollenction.document(today)
            val articleCollection = todayDocument.collection("article").document()

            article.id = articleCollection.id
            articleCollection
                .set(article)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("dataSource", "addArticle2Firebase: $article")

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

    override suspend fun getArticle(userEmail: String, collection: String): Result<List<Article>> =
    suspendCoroutine { continuation ->

        val today = Calendar.getInstance().timeInMillis.toDisplayFormat()
        val firestore = FirebaseFirestore.getInstance().collection(PATH_USERS)
        val userDocument = firestore.document(userEmail)
        val greensCollenction = userDocument.collection(PATH_GREENS)
        val todayDocument = greensCollenction.document(today)
        val articleCollection = todayDocument.collection("article")

        articleCollection
            .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Article>()
                    for (document in task.result!!) {
                        Log.d("getArticle", document.id + " => " + document.data)

                        val article = document.toObject(Article::class.java)
                        list.add(article)
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

    override suspend fun addEventInfo2UserFirebase(event: Event,eventId: String, eventDay: String,userEmail: String): Result<Boolean> =
        suspendCoroutine { continuation ->

            val today = Calendar.getInstance().timeInMillis.toDisplayFormat()

            val firestore = FirebaseFirestore.getInstance().collection(PATH_USERS)
            val userDocument = firestore.document(userEmail)
            val greensCollenction = userDocument.collection(PATH_GREENS)
            val document = greensCollenction.document(event.eventTime)

            val data = hashMapOf(
                "event" to "event",
                "introduction" to event.introduction.toString(),
                "year" to event.eventYear.toString(),
                "month" to event.eventMonth.toString(),
                "day" to event.eventDay.toString(),
                "createdTime" to event.eventTimestamp,
            )


            document.set(data, SetOptions.merge())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("dataSource", "addEventMember: $task")
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