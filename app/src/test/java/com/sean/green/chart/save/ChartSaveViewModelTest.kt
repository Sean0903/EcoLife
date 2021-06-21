package com.sean.green.chart.save

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sean.green.data.Save
import com.sean.green.data.source.FakeRepository
import getOrAwaitValue
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ChartSaveViewModelTest {

    private lateinit var fakeRepository: FakeRepository
    private lateinit var viewModel: ChartSaveViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setViewModel() {
        fakeRepository = FakeRepository()
        viewModel = ChartSaveViewModel(fakeRepository)
    }

    @Test
    fun setSaveDataForChart() {
//        val saveList = mutableListOf(
//            Save("1", 4, 5, 3, "",123,""),
//            Save("2", 1, 2, 2, "",456,""),
//            Save("3", 3, 1, 4, "",789,""),
//            Save("4", 4, 6, 6, "",1011,"")
//        )

        viewModel.setSaveDataForChart()

        val value = viewModel.saveDataSevenDays.getOrAwaitValue()

        assertEquals(Save("1", 4, 5, 3, "",123,""),value)
        assertEquals(Save("2", 1, 2, 2, "",456,""),value)
        assertEquals(Save("3", 3, 1, 4, "",789,""),value)
        assertEquals(Save("4", 4, 6, 6, "",1011,""),value)
    }
}