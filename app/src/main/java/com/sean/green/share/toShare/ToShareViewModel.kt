package com.sean.green.share.toShare

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.sean.green.GreenApplication
import com.sean.green.R
import com.sean.green.data.FirebaseKey.Companion.COLLECTION_SHARE
import com.sean.green.data.FirebaseKey.Companion.COLLECTION_USERS
import com.sean.green.data.FirebaseKey.Companion.CREATEDTIME
import com.sean.green.data.FirebaseKey.Companion.DAY
import com.sean.green.data.FirebaseKey.Companion.MONTH
import com.sean.green.data.FirebaseKey.Companion.SHARE
import com.sean.green.data.FirebaseKey.Companion.YEAR
import com.sean.green.data.Result
import com.sean.green.data.Share
import com.sean.green.data.User
import com.sean.green.data.source.GreenRepository
import com.sean.green.network.LoadApiStatus
import com.sean.green.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class ToShareViewModel(private val repository: GreenRepository): ViewModel() {

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _userImage = MutableLiveData<List<User>>()

    val userImage: LiveData<List<User>>
        get() = _userImage

    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String?>()

    val error: LiveData<String?>
        get() = _error

    // status for the loading icon of swl
    private val _refreshStatus = MutableLiveData<Boolean>()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    val achievement = MutableLiveData<String>()
    val time = MutableLiveData<String>()
    val content = MutableLiveData<String>()

    fun addSharingData2Firebase(userEmail: String, userImage: String, userName: String) {

        coroutineScope.launch {

            val data = hashMapOf(
                DAY to Util.day,
                MONTH to Util.month,
                YEAR to Util.year,
                CREATEDTIME to Util.createdTime,
                SHARE to SHARE
            )

            val saveTime = FirebaseFirestore.getInstance()
                .collection(COLLECTION_USERS).document(userEmail).
                collection("greens").document(Util.today).set(data, SetOptions.merge())

            val newShareData = Share(
                achievement = achievement.value?.toString(),
                time = time.value?.toString(),
                content = content.value?.toString(),
                createdTime = Calendar.getInstance().timeInMillis,
                today = Util.today,
                image = userImage,
                name = userName,
                email = userEmail
            )

            when (val result = repository.addSharing2Firebase(COLLECTION_SHARE,newShareData)) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                }
                else -> {
                    _error.value = GreenApplication.instance.getString(R.string.Please_try_again_later)
                    _status.value = LoadApiStatus.ERROR
                }
            }
        }
    }
}