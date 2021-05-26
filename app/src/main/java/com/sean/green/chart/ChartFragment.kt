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
import kotlinx.android.synthetic.main.fragment_chart.*
import kotlinx.android.synthetic.main.item_share.*
import kotlinx.android.synthetic.main.item_share.lineChart1

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

            val mock1 = Save("",2,3,6,"",20210524)
            val mock2 = Save("",5,8,7,"",20210525)
            val mock3 = Save("",10,24,5,"",20210526)
            val mock4 = Save("",29,18,16,"",20210527)
            val mock5 = Save("",11,4,8,"",20210528)
            val mock6 = Save("",40,16,22,"",20210527)
            val mock7 = Save("",6,17,27,"",20210528)

            val mockList1 = listOf( mock1, mock2,mock3, mock4,mock5,mock6,mock7)

            adapter.submitList(mockList1)

            val adapter1 = ChartDateUseAdapter()
            binding.recyclerViewChartPageUse.adapter = adapter1

            val mock8 = Use("2021.5.28",20,22,22)
            val mock9 = Use("2021.5.29",10,24,30)
            val mock10 = Use("2021.5.28",22,36,21)
            val mock11 = Use("2021.5.29",31,23,32)
            val mock12 = Use("2021.5.28",50,34,21)
            val mock13 = Use("2021.5.29",32,33,26)
            val mock14 = Use("2021.5.28",22,22,28)


            val mockList2 = listOf( mock8, mock9,mock10,mock11,mock12,mock13,mock14)

            adapter1.submitList(mockList2)

            (activity as MainActivity).dismissFabButton(true)

            binding.button3.setOnClickListener {
                setLineChartData()
            }

            binding.button4.setOnClickListener {
                setLineChartData2()
            }

            return binding.root
        }

    fun setLineChartData(){

        val xvalue = ArrayList<String>()
        xvalue.add("一")
        xvalue.add("二")
        xvalue.add("三")
        xvalue.add("四")
        xvalue.add("五")
        xvalue.add("六")
        xvalue.add("日")

        val lineentrySavePlastic = ArrayList<Entry>();
        lineentrySavePlastic.add(Entry(2f, 0))
        lineentrySavePlastic.add(Entry(5f, 1))
        lineentrySavePlastic.add(Entry(10f, 2))
        lineentrySavePlastic.add(Entry(29f, 3))
        lineentrySavePlastic.add(Entry(11f, 4))
        lineentrySavePlastic.add(Entry(40f, 5))
        lineentrySavePlastic.add(Entry(6f, 6))

        val lineentrySavePower = ArrayList<Entry>();
        lineentrySavePower.add(Entry(3f, 0))
        lineentrySavePower.add(Entry(8f, 1))
        lineentrySavePower.add(Entry(24f, 2))
        lineentrySavePower.add(Entry(18f, 3))
        lineentrySavePower.add(Entry(4f, 4))
        lineentrySavePower.add(Entry(16f, 5))
        lineentrySavePower.add(Entry(17f, 6))

        val lineentrySaveCarbon = ArrayList<Entry>();
        lineentrySaveCarbon.add(Entry(6f, 0))
        lineentrySaveCarbon.add(Entry(7f, 1))
        lineentrySaveCarbon.add(Entry(5f, 2))
        lineentrySaveCarbon.add(Entry(16f, 3))
        lineentrySaveCarbon.add(Entry(8f, 4))
        lineentrySaveCarbon.add(Entry(22f, 5))
        lineentrySaveCarbon.add(Entry(27f, 6))

        val linedatasetSavePlastic = LineDataSet(lineentrySavePlastic,"Plastic")
        linedatasetSavePlastic.color = resources.getColor(R.color.colorBlue5)

        val linedatasetSavePower = LineDataSet(lineentrySavePower,"Power")
        linedatasetSavePower.color = resources.getColor(R.color.colorSelectedBottomNav)

        val linedatasetSaveCarbon = LineDataSet(lineentrySaveCarbon,"Carbon")
        linedatasetSaveCarbon.color = resources.getColor(R.color.colorNight)



//            linedataset.circleRadius = 0f
//            linedataset.setDrawFilled(true)
//            linedataset.fillColor = resources.getColor(R.color.black)
//            linedataset.fillAlpha = 30

        val finaldataset = ArrayList<LineDataSet>()
        finaldataset.add(linedatasetSavePlastic)
        finaldataset.add(linedatasetSavePower)
        finaldataset.add(linedatasetSaveCarbon)

        val data = LineData(xvalue, finaldataset as List<ILineDataSet>?)

        lineChart1.data = data
        lineChart1.setBackgroundColor(resources.getColor(R.color.white))
        lineChart1.animateXY(3000,3000)

    }

    fun setLineChartData2(){

        val xvalue = ArrayList<String>()
        xvalue.add("一")
        xvalue.add("二")
        xvalue.add("三")
        xvalue.add("四")
        xvalue.add("五")
        xvalue.add("六")
        xvalue.add("日")

        val lineentrySavePlastic = ArrayList<Entry>();
        lineentrySavePlastic.add(Entry(20f, 0))
        lineentrySavePlastic.add(Entry(10f, 1))
        lineentrySavePlastic.add(Entry(22f, 2))
        lineentrySavePlastic.add(Entry(31f, 3))
        lineentrySavePlastic.add(Entry(50f, 4))
        lineentrySavePlastic.add(Entry(32f, 5))
        lineentrySavePlastic.add(Entry(22f, 6))

        val lineentrySavePower = ArrayList<Entry>();
        lineentrySavePower.add(Entry(22f, 0))
        lineentrySavePower.add(Entry(24f, 1))
        lineentrySavePower.add(Entry(36f, 2))
        lineentrySavePower.add(Entry(23f, 3))
        lineentrySavePower.add(Entry(34f, 4))
        lineentrySavePower.add(Entry(33f, 5))
        lineentrySavePower.add(Entry(22f, 6))

        val lineentrySaveCarbon = ArrayList<Entry>();
        lineentrySaveCarbon.add(Entry(22f, 0))
        lineentrySaveCarbon.add(Entry(30f, 1))
        lineentrySaveCarbon.add(Entry(21f, 2))
        lineentrySaveCarbon.add(Entry(32f, 3))
        lineentrySaveCarbon.add(Entry(21f, 4))
        lineentrySaveCarbon.add(Entry(26f, 5))
        lineentrySaveCarbon.add(Entry(28f, 6))

        val linedatasetSavePlastic = LineDataSet(lineentrySavePlastic,"Plastic")
        linedatasetSavePlastic.color = resources.getColor(R.color.colorBlue5)

        val linedatasetSavePower = LineDataSet(lineentrySavePower,"Power")
        linedatasetSavePower.color = resources.getColor(R.color.colorSelectedBottomNav)

        val linedatasetSaveCarbon = LineDataSet(lineentrySaveCarbon,"Carbon")
        linedatasetSaveCarbon.color = resources.getColor(R.color.colorNight)



//            linedataset.circleRadius = 0f
//            linedataset.setDrawFilled(true)
//            linedataset.fillColor = resources.getColor(R.color.black)
//            linedataset.fillAlpha = 30

        val finaldataset = ArrayList<LineDataSet>()
        finaldataset.add(linedatasetSavePlastic)
        finaldataset.add(linedatasetSavePower)
        finaldataset.add(linedatasetSaveCarbon)

        val data = LineData(xvalue, finaldataset as List<ILineDataSet>?)

        lineChart2.data = data
        lineChart2.setBackgroundColor(resources.getColor(R.color.white))
        lineChart2.animateXY(3000,3000)

    }

}