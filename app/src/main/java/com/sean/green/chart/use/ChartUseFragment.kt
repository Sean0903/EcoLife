package com.sean.green.chart.use

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.sean.green.R
import com.sean.green.databinding.FragmentUseChartBinding
import com.sean.green.ext.getVmFactory
import kotlinx.android.synthetic.main.fragment_use_chart.*

class ChartUseFragment : Fragment() {

    private lateinit var binding: FragmentUseChartBinding
    private val viewModel by viewModels<ChartUseViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUseChartBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = ChartUseAdapter()
        binding.recyclerViewChartPageUse.adapter = adapter

        viewModel.useDataForRecycleView.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("seanChartUseFragment", "it = ${it}")
                adapter.notifyDataSetChanged()
                adapter.submitList(it)
            }
        })

        binding.imageChartPageCalender.setOnClickListener {
            setLineChartData()
        }

        var count = 0

        viewModel.useDataSevenDays.observe(viewLifecycleOwner, Observer {
            viewModel.setUseDataForChart()
            count++
            if (count == 7) {
                setLineChartData()
                binding.lottieChart.visibility = View.GONE
                binding.imageChartPageUsePlastic.visibility = View.VISIBLE
                binding.imageChartPageUsePower.visibility = View.VISIBLE
                binding.imageChartPageUseCarbon.visibility = View.VISIBLE
                binding.textChartPageUsePlastic.visibility = View.VISIBLE
                binding.textChartPageUsePower.visibility = View.VISIBLE
                binding.textChartPageUseCarbon.visibility = View.VISIBLE
                binding.imageChartPageCalender.visibility = View.VISIBLE
                binding.imageChartPageUsePlastic2.visibility = View.VISIBLE
                binding.imageChartPageUsePower2.visibility = View.VISIBLE
                binding.imageChartPageUseCarbon2.visibility = View.VISIBLE
                binding.recyclerViewChartPageUse.visibility = View.VISIBLE
                binding.textCalendarTitle.visibility = View.VISIBLE
                binding.textPlasticTitle.visibility = View.VISIBLE
                binding.textPowerTitle.visibility = View.VISIBLE
                binding.textCarbonTitle.visibility = View.VISIBLE
            }
        })

        binding.lottieChart.repeatCount = -1
        // 开始播放动画
        binding.lottieChart.playAnimation()
        binding.imageChartPageUsePlastic.visibility = View.INVISIBLE
        binding.imageChartPageUsePower.visibility = View.INVISIBLE
        binding.imageChartPageUseCarbon.visibility = View.INVISIBLE
        binding.textChartPageUsePlastic.visibility = View.INVISIBLE
        binding.textChartPageUsePower.visibility = View.INVISIBLE
        binding.textChartPageUseCarbon.visibility = View.INVISIBLE
        binding.imageChartPageCalender.visibility = View.INVISIBLE
        binding.imageChartPageUsePlastic2.visibility = View.INVISIBLE
        binding.imageChartPageUsePower2.visibility = View.INVISIBLE
        binding.imageChartPageUseCarbon2.visibility = View.INVISIBLE
        binding.recyclerViewChartPageUse.visibility = View.INVISIBLE
        binding.textCalendarTitle.visibility = View.INVISIBLE
        binding.textPlasticTitle.visibility = View.INVISIBLE
        binding.textPowerTitle.visibility = View.INVISIBLE
        binding.textCarbonTitle.visibility = View.INVISIBLE

        return binding.root
    }

    fun setLineChartData() {

        val xvalue = ArrayList<String>()
        xvalue.add("")
        xvalue.add("")
        xvalue.add("")
        xvalue.add("")
        xvalue.add("")
        xvalue.add("")
        xvalue.add("")

        val lineentrySavePlastic = ArrayList<Entry>();
        lineentrySavePlastic.add(Entry(viewModel.plasticList[6].toFloat(), 0))
        lineentrySavePlastic.add(Entry(viewModel.plasticList[5].toFloat(), 1))
        lineentrySavePlastic.add(Entry(viewModel.plasticList[4].toFloat(), 2))
        lineentrySavePlastic.add(Entry(viewModel.plasticList[3].toFloat(), 3))
        lineentrySavePlastic.add(Entry(viewModel.plasticList[2].toFloat(), 4))
        lineentrySavePlastic.add(Entry(viewModel.plasticList[1].toFloat(), 5))
        lineentrySavePlastic.add(Entry(viewModel.plasticList[0].toFloat(), 6))

        val lineentrySavePower = ArrayList<Entry>();
        lineentrySavePower.add(Entry(viewModel.powerList[6].toFloat(), 0))
        lineentrySavePower.add(Entry(viewModel.powerList[5].toFloat(), 1))
        lineentrySavePower.add(Entry(viewModel.powerList[4].toFloat(), 2))
        lineentrySavePower.add(Entry(viewModel.powerList[3].toFloat(), 3))
        lineentrySavePower.add(Entry(viewModel.powerList[2].toFloat(), 4))
        lineentrySavePower.add(Entry(viewModel.powerList[1].toFloat(), 5))
        lineentrySavePower.add(Entry(viewModel.powerList[0].toFloat(), 6))

        val lineentrySaveCarbon = ArrayList<Entry>();
        lineentrySaveCarbon.add(Entry(viewModel.carbonList[6].toFloat(), 0))
        lineentrySaveCarbon.add(Entry(viewModel.carbonList[5].toFloat(), 1))
        lineentrySaveCarbon.add(Entry(viewModel.carbonList[4].toFloat(), 2))
        lineentrySaveCarbon.add(Entry(viewModel.carbonList[3].toFloat(), 3))
        lineentrySaveCarbon.add(Entry(viewModel.carbonList[2].toFloat(), 4))
        lineentrySaveCarbon.add(Entry(viewModel.carbonList[1].toFloat(), 5))
        lineentrySaveCarbon.add(Entry(viewModel.carbonList[0].toFloat(), 6))

        val linedatasetSavePlastic = LineDataSet(lineentrySavePlastic, "Plastic")
        linedatasetSavePlastic.color = resources.getColor(R.color.colorBlue5)

        val linedatasetSavePower = LineDataSet(lineentrySavePower, "Power")
        linedatasetSavePower.color = resources.getColor(R.color.colorSelectedBottomNav)

        val linedatasetSaveCarbon = LineDataSet(lineentrySaveCarbon, "Carbon")
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
        lineChart2.animateXY(3000, 3000)

    }
}