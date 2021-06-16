package com.sean.green

import android.app.Application
import android.content.Context
import app.appworks.school.stylish.util.ServiceLocator
import com.sean.green.data.source.GreenRepository
import kotlin.properties.Delegates

// To solve providing a non-null ‘prefs’ object to our entire app
class App : Application() {

    val greenRepository: GreenRepository
        get() = ServiceLocator.provideTasksRepository(this)

    companion object {

        var instance: App by Delegates.notNull()
        lateinit var appContext : Context

//        var instance : App? = null
//
//        fun applicationContext() : Context {
//            return instance!!.applicationContext
//        }
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        instance = this

    }
}