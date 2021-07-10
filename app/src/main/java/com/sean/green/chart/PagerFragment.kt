package com.sean.green.chart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sean.green.databinding.PagerChartBinding

class PagerFragment : Fragment() {

    lateinit var binding: PagerChartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //pager show on page
        binding = PagerChartBinding.inflate(inflater, container, false)
        //binding pager with pagerAdapter
        binding.chartPager.adapter = PagerAdapter(childFragmentManager)
        //tab show on page and slide page can go to next page
        binding.chartTab.setupWithViewPager(binding.chartPager)

        return binding.root
    }
}