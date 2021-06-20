package com.sean.green.chart.save

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sean.green.data.Save
import com.sean.green.data.source.FakeRepository
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ChartSaveViewModelTest {

    private lateinit var testRepository: FakeRepository
    private lateinit var viewModel: ChartSaveViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        testRepository = FakeRepository()
        viewModel = ChartSaveViewModel(testRepository)

    }

    @Test
    fun setSaveDataForChart() {
        val saveList = mutableListOf<Save>(
            Save("1", 4, 5, 3, ""),
            Save("3", 1, 2, 2, ""),
            Save("5", 3, 1, 4, ""),
            Save("7", 4, 6, 6, "")
        )

        val result = viewModel.setSaveDataForChart(saveList)
        assertEquals(result[0], Save("2", 4, 5, 3, ""))
        assertEquals(result[1], Save("4", 1, 2, 2, ""))
        assertEquals(result[2], Save("6", 3, 1, 4, ""))
        assertEquals(result[3], Save("8", 4, 6, 6, ""))
    }
}