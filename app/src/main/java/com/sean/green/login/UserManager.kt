package com.sean.green.login

import android.content.SharedPreferences
import android.util.Log
import com.sean.green.GreenApplication
import com.sean.green.data.User


const val tagUserUid = "uid"
const val tagUserToken = "token"

object UserManager {

    var user = User()

    var prefs : SharedPreferences? = GreenApplication.instance?.getSharedPreferences(tagUserToken, 0)

    var uid : String? = null
        get(){
            return prefs?.getString(
                tagUserUid, "")
        }
        set(value){
            field = prefs?.edit()?.putString(
                tagUserUid,value)?.apply().toString()
            Log.i("UserManager.Uid", value!!)
        }
}