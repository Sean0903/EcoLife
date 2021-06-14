package com.sean.green

import android.app.Application
import android.content.Context
import app.appworks.school.stylish.util.ServiceLocator
import com.sean.green.data.source.GreenRepository
import kotlin.properties.Delegates


/**
 *
 * An application that lazily provides a repository. Note that this Service Locator pattern is
 * used to simplify the sample. Consider a Dependency Injection framework.
 */

class GreenApplication: Application() {

    val greenRepository: GreenRepository
        get() = ServiceLocator.provideTasksRepository(this)

    companion object {
        var instance: GreenApplication by Delegates.notNull()
        lateinit var appContext : Context

        fun applicationContext() : Context {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        instance = this
    }
}