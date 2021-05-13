package com.sean.green.ext

import android.app.Activity
import android.view.Gravity
import android.widget.Toast
import com.sean.green.GreenApplication
import com.sean.green.factory.ViewModelFactory


/**
 *
 * Extension functions for Activity.
 */
fun Activity.getVmFactory(): ViewModelFactory {
    val repository = (applicationContext as GreenApplication).greenRepository
    return ViewModelFactory(repository)
}

fun Activity?.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).apply {
        setGravity(Gravity.CENTER, 0, 0)
        show()
    }
}