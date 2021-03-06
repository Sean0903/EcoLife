package com.sean.green.chart.save

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
import com.sean.green.databinding.FragmentSaveChartBinding
import com.sean.green.ext.getVmFactory
import kotlinx.android.synthetic.main.fragment_save_chart.*

class ChartSaveFragment : Fragment() {

    private lateinit var binding: FragmentSaveChartBinding

    private val viewModel by viewModels<ChartSaveViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSaveChartBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = ChartSaveAdapter()
        binding.recyclerViewChartPageSave.adapter = adapter

        viewModel.saveDataForRecycleView.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("seanChartSaveFragment", "it = ${it}")
                adapter.notifyDataSetChanged()
                adapter.submitList(it)
            }
        })

        binding.imageChartPageCalender.setOnClickListener {
            setLineChartData()
        }

        //need refactor
        var count = 0
        viewModel.saveDataSevenDays.observe(viewLifecycleOwner, Observer {
            viewModel.setSaveDataForChart()

            count++
            if (count == 7) {

                setLineChartData()
                makeViewInvisible()

            }
        })

        makeViewVisible()

        return binding.root
    }

    val xvalue = ArrayList<String>()
    val lineentrySavePlastic = ArrayList<Entry>();
    val lineentrySavePower = ArrayList<Entry>();
    val lineentrySaveCarbon = ArrayList<Entry>();

    //need refactor
    fun setLineChartData() {

        xvalue.add("")
        xvalue.add("")
        xvalue.add("")
        xvalue.add("")
        xvalue.add("")
        xvalue.add("")
        xvalue.add("")

        lineentrySavePlastic.add(Entry(viewModel.plasticList[6].toFloat(), 0))
        lineentrySavePlastic.add(Entry(viewModel.plasticList[5].toFloat(), 1))
        lineentrySavePlastic.add(Entry(viewModel.plasticList[4].toFloat(), 2))
        lineentrySavePlastic.add(Entry(viewModel.plasticList[3].toFloat(), 3))
        lineentrySavePlastic.add(Entry(viewModel.plasticList[2].toFloat(), 4))
        lineentrySavePlastic.add(Entry(viewModel.plasticList[1].toFloat(), 5))
        lineentrySavePlastic.add(Entry(viewModel.plasticList[0].toFloat(), 6))

        lineentrySavePower.add(Entry(viewModel.powerList[6].toFloat(), 0))
        lineentrySavePower.add(Entry(viewModel.powerList[5].toFloat(), 1))
        lineentrySavePower.add(Entry(viewModel.powerList[4].toFloat(), 2))
        lineentrySavePower.add(Entry(viewModel.powerList[3].toFloat(), 3))
        lineentrySavePower.add(Entry(viewModel.powerList[2].toFloat(), 4))
        lineentrySavePower.add(Entry(viewModel.powerList[1].toFloat(), 5))
        lineentrySavePower.add(Entry(viewModel.powerList[0].toFloat(), 6))

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

        val finaldataset = ArrayList<LineDataSet>()
        finaldataset.add(linedatasetSavePlastic)
        finaldataset.add(linedatasetSavePower)
        finaldataset.add(linedatasetSaveCarbon)

        val data = LineData(xvalue, finaldataset as List<ILineDataSet>?)

        lineChart1.data = data
        lineChart1.setBackgroundColor(resources.getColor(R.color.white))
        lineChart1.animateXY(3000, 3000)

    }

    private fun makeViewInvisible() {
        binding.lottieChart.visibility = View.GONE
        binding.imageChartPageSavePlastic.visibility = View.VISIBLE
        binding.imageChartPageSavePower.visibility = View.VISIBLE
        binding.imageChartPageSaveCarbon.visibility = View.VISIBLE
        binding.textChartPageSavePlastic.visibility = View.VISIBLE
        binding.textChartPageSavePower.visibility = View.VISIBLE
        binding.textChartPageSaveCarbon.visibility = View.VISIBLE
        binding.imageChartPageCalender.visibility = View.VISIBLE
        binding.imageChartPageSavePlastic2.visibility = View.VISIBLE
        binding.imageChartPageSavePower2.visibility = View.VISIBLE
        binding.imageChartPageSaveCarbon2.visibility = View.VISIBLE
        binding.recyclerViewChartPageSave.visibility = View.VISIBLE
        binding.textCalendarTitle.visibility = View.VISIBLE
        binding.textPlasticTitle.visibility = View.VISIBLE
        binding.textPowerTitle.visibility = View.VISIBLE
        binding.textCarbonTitle.visibility = View.VISIBLE
    }

    private fun makeViewVisible() {
        binding.lottieChart.repeatCount = -1
        binding.lottieChart.playAnimation()
        binding.imageChartPageSavePlastic.visibility = View.INVISIBLE
        binding.imageChartPageSavePower.visibility = View.INVISIBLE
        binding.imageChartPageSaveCarbon.visibility = View.INVISIBLE
        binding.textChartPageSavePlastic.visibility = View.INVISIBLE
        binding.textChartPageSavePower.visibility = View.INVISIBLE
        binding.textChartPageSaveCarbon.visibility = View.INVISIBLE
        binding.imageChartPageCalender.visibility = View.INVISIBLE
        binding.imageChartPageSavePlastic2.visibility = View.INVISIBLE
        binding.imageChartPageSavePower2.visibility = View.INVISIBLE
        binding.imageChartPageSaveCarbon2.visibility = View.INVISIBLE
        binding.recyclerViewChartPageSave.visibility = View.INVISIBLE
        binding.textCalendarTitle.visibility = View.INVISIBLE
        binding.textPlasticTitle.visibility = View.INVISIBLE
        binding.textPowerTitle.visibility = View.INVISIBLE
        binding.textCarbonTitle.visibility = View.INVISIBLE
    }
}