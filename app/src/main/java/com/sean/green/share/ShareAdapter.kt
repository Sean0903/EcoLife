package com.sean.green.share

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sean.green.data.Share
import com.sean.green.databinding.ItemShareBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.sean.green.R
import com.sean.green.data.User
import com.sean.green.util.Util
import com.sean.green.util.Util.getColor
import kotlinx.android.synthetic.main.item_share.view.*


class ShareAdapter(private val viewModel: ShareViewModel) :
    ListAdapter<Share, ShareAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder.from(parent)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(viewModel, getItem(position))


    }

    class ViewHolder(var binding: ItemShareBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: ShareViewModel, item: Share) {
            Log.d("seanViewHolder", "fun bind(item: Save) = $item")
            binding.shareData = item
//            binding.image = item
            binding.lineChart1

            fun setLine() {
                val xvalue = ArrayList<String>()
                xvalue.add("")
                xvalue.add("")
                xvalue.add("")
                xvalue.add("")
                xvalue.add("")
                xvalue.add("")
                xvalue.add("")

                val lineentrySavePlastic = ArrayList<Entry>();
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
                linedatasetSavePlastic.color = getColor(R.color.colorBlue5)

                val linedatasetSavePower = LineDataSet(lineentrySavePower, "Power")
                linedatasetSavePower.color = getColor(R.color.colorSelectedBottomNav)

                val linedatasetSaveCarbon = LineDataSet(lineentrySaveCarbon, "Carbon")
                linedatasetSaveCarbon.color = getColor(R.color.colorNight)

                val finaldataset = ArrayList<LineDataSet>()
                finaldataset.add(linedatasetSavePlastic)
                finaldataset.add(linedatasetSavePower)
                finaldataset.add(linedatasetSaveCarbon)

                val data = LineData(xvalue, finaldataset as List<ILineDataSet>?)

                binding.lineChart1.data = data
                binding.lineChart1.setBackgroundColor(Util.getColor(R.color.white))
                binding.lineChart1.animateXY(3000, 3000)
            }

            binding.textView9.setOnClickListener {
                setLine()
            }

            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemShareBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Share>() {
        override fun areItemsTheSame(oldItem: Share, newItem: Share): Boolean {
            return oldItem == newItem
        }


        override fun areContentsTheSame(oldItem: Share, newItem: Share): Boolean {
            return oldItem == newItem
        }
    }
}

