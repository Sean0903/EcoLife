package com.sean.green.chart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.sean.green.MainActivity
import com.sean.green.R
import com.sean.green.data.Save
import com.sean.green.data.Use
import com.sean.green.databinding.FragmentChartBinding
import com.sean.green.home.HomeAdapter
import kotlinx.android.synthetic.main.item_share.*

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

            binding.button3.setOnClickListener {
                setLineChartData()
            }





            return binding.root
        }

    fun setLineChartData(){

        val xvalue = ArrayList<String>()
        xvalue.add("11.00 AM")
        xvalue.add("12.00 AM")
        xvalue.add("1.00 AM")
        xvalue.add("3.00 PM")
        xvalue.add("7.00 PM")


        val lineentry = ArrayList<Entry>();
        lineentry.add(Entry(20f, 0))
        lineentry.add(Entry(50f, 1))
        lineentry.add(Entry(60f, 2))
        lineentry.add(Entry(30f, 3))
        lineentry.add(Entry(10f, 4))

        val lineentry1 = ArrayList<Entry>();
        lineentry1.add(Entry(10f, 0))
        lineentry1.add(Entry(40f, 1))
        lineentry1.add(Entry(20f, 2))
        lineentry1.add(Entry(70f, 3))
        lineentry1.add(Entry(30f, 4))


        val linedataset = LineDataSet(lineentry,"First")
        linedataset.color = resources.getColor(R.color.brightGreen1)

        val linedataset1 = LineDataSet(lineentry,"Second")
        linedataset.color = resources.getColor(R.color.blue_voyage)

//            linedataset.circleRadius = 0f
//            linedataset.setDrawFilled(true)
//            linedataset.fillColor = resources.getColor(R.color.black)
//            linedataset.fillAlpha = 30

        val finaldataset = ArrayList<LineDataSet>()
        finaldataset.add(linedataset)
        finaldataset.add((linedataset1))

        val data = LineData(xvalue, finaldataset as List<ILineDataSet>?)

        lineChart.data = data
        lineChart.setBackgroundColor(resources.getColor(R.color.white))
        lineChart.animateXY(3000,3000)

    }

}