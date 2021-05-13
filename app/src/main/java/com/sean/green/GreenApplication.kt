package com.sean.green

import android.app.Application
import kotlin.properties.Delegates
import com.sean.green.data.GreenRepository


/**
 *
 * An application that lazily provides a repository. Note that this Service Locator pattern is
 * used to simplify the sample. Consider a Dependency Injection framework.
 */

//class GreenApplication: Application() {
//
//
//    // Depends on the flavor,
//    val environmentRepository: GreenRepository
//        get() = ServiceLocator.provideTasksRepository(this)
//
//    companion object {
//        var instance: GreenApplication by Delegates.notNull()
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        instance = this
//    }
//}