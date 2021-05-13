package com.sean.green.chart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sean.green.databinding.FragmentChartBinding

class ChartFragment: Fragment() {

        private lateinit var binding : FragmentChartBinding
        private val viewModel : ChartViewModel by lazy {
            ViewModelProvider(this).get(ChartViewModel::class.java)
        }
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = FragmentChartBinding.inflate(inflater)
            binding.lifecycleOwner = this
            binding.viewModel = viewModel
            return binding.root
        }

}