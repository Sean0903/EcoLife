package com.sean.green.chart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sean.green.databinding.ActivityMainBinding.inflate
import com.sean.green.databinding.DialogChallengeBinding.inflate
import com.sean.green.databinding.PagerChartBinding

class PagerFragment(): Fragment() {

    lateinit var binding: PagerChartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //將pager在此處實現
        binding = PagerChartBinding.inflate(inflater,container,false)
        //賦予pager實際功能，功能寫在PagerAdapter中
        binding.chartPager.adapter = PagerAdapter(childFragmentManager)
        //讓Tab顯示出來，並且會隨pager的滑動跳頁
        binding.chartTab.setupWithViewPager(binding.chartPager)

        return binding.root
    }
}