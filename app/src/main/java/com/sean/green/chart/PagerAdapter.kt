package com.sean.green.chart

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sean.green.chart.save.ChartSaveFragment
import com.sean.green.chart.use.ChartUseFragment

class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ChartSaveFragment()
            1 -> ChartUseFragment()
            else -> ChartSaveFragment()
        }
    }

    override fun getCount() = ChartPager.values().size

    override fun getPageTitle(position: Int): CharSequence? {
        return ChartPager.values()[position].value
    }
}

enum class ChartPager(val value: String) {
    Save("本週成果"),
    Use("本週消耗")
}