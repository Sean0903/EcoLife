package com.sean.green.share

import android.app.Dialog
import android.os.Bundle
import android.util.Log
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
import com.sean.green.data.Share
import com.sean.green.databinding.FragmentShareBinding
import kotlinx.android.synthetic.main.item_share.*

class ShareFragment: Fragment() {

    private lateinit var binding : FragmentShareBinding
    private val viewModel : ShareViewModel by lazy {
        ViewModelProvider(this).get(ShareViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShareBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = ShareAdapter()
        binding.recyclerViewShare.adapter = adapter

        val mock3 = Share("Sean","一週都騎腳踏車","2021.5.18","大眾交通擔心染疫，開車又有碳排，我一週都騎腳踏車上班。")
        val mock4 = Share("Nute","一週不喝手搖飲","2021.5.20","少用了很多吸管跟塑膠杯，除非自己攜帶杯子，不然我都不買手搖飲了。")
        val mock5 = Share ("Mack","接雨水來喝","2021.5.30","南部最近缺水，我直接收集雨水來喝，節省水資源。")
        val mockList1 = listOf( mock3, mock4,mock5)

        adapter.submitList(mockList1)

        binding.fab.setOnClickListener {
            var dialogShare = Dialog(this.requireContext())
            val view = layoutInflater.inflate(R.layout.dialog_share, null)
            dialogShare.setContentView(view)
            dialogShare.show()
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
            linedataset.color = resources.getColor(R.color.brightYellow)

            val linedataset1 = LineDataSet(lineentry1,"Second")
            linedataset1.color = resources.getColor(R.color.brightGreen1)
            Log.d("checkChart","lineentry1 = $lineentry1")

//            linedataset.circleRadius = 0f
//            linedataset.setDrawFilled(true)
//            linedataset.fillColor = resources.getColor(R.color.black)
//            linedataset.fillAlpha = 30

            val finaldataset = ArrayList<LineDataSet>()
            finaldataset.add(linedataset)
            finaldataset.add(linedataset1)


            val data = LineData(xvalue, finaldataset as List<ILineDataSet>?)
            lineChart1.data = data
            lineChart1.setBackgroundColor(resources.getColor(R.color.white))
            lineChart1.animateXY(3000,3000)
            Log.d("checkChart","data = ${lineChart1.data}")

        }



        (activity as MainActivity).dismissFabButton(false)

        return binding.root
    }
}