package com.sean.green.use

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sean.green.data.source.FakeRepository
import getOrAwaitValue
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class UseViewModelTestSetPhoto {

    private lateinit var fakeRepository: FakeRepository
    private lateinit var viewModel: UseViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        fakeRepository = FakeRepository()
        viewModel = UseViewModel(fakeRepository)
    }

    @Test
    fun testUri() {
        val uri = Uri.parse("myapp://home/payments")
        assertTrue(uri == null)
    }

    @Test
    fun setPhoto() {

        val photo: Uri? = Uri.parse("myapp://home/payments")

        viewModel.setPhoto(photo)

        val value = viewModel.photoUri.getOrAwaitValue()

        assertEquals(null,value)

    }

}