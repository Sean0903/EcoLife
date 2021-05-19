package com.sean.green.chart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sean.green.MainActivity
import com.sean.green.data.Save
import com.sean.green.data.Use
import com.sean.green.databinding.FragmentChartBinding
import com.sean.green.home.HomeAdapter

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

            val adapter = ChartDateSaveAdapter()
            binding.recyclerViewChartPageSave.adapter = adapter

            val mock3 = Save("2021.5.18","20","30","40")
            val mock4 = Save("2021.5.19","10","20","30")
            val mockList1 = listOf( mock3, mock4)

            adapter.submitList(mockList1)

            val adapter1 = ChartDateUseAdapter()
            binding.recyclerViewChartPageUse.adapter = adapter1

            val mock5 = Use("2021.5.28","200","300","400")
            val mock6 = Use("2021.5.29","100","200","300")
            val mockList2 = listOf( mock5, mock6)

            adapter1.submitList(mockList2)

            (activity as MainActivity).dismissFabButton(true)


            return binding.root
        }

}