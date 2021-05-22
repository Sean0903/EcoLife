package com.sean.green.share

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sean.green.data.Share
import com.sean.green.databinding.ItemShareBinding

class ShareDialogFragment : Fragment() {


    private lateinit var binding: ItemShareBinding
//    private val viewModel: ShareViewModel by lazy {
//        ViewModelProvider(this).get(ShareViewModel::class.java)
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ItemShareBinding.inflate(inflater)
        binding.lifecycleOwner = this
//        binding.viewModel = viewModel

        val adapter = ShareDialogAdapter()
        binding.recycleViewShareDialog.adapter = adapter

//        val adapter2 = ShareDialogAdapter()
//        binding.

//        val mock3 = Share("Sean", "一週都騎腳踏車", "2021.5.18", "大眾交通擔心染疫，開車又有碳排，我一週都騎腳踏車上班。")
//        val mock4 = Share("Nute", "一週不喝手搖飲", "2021.5.20", "少用了很多吸管跟塑膠杯，除非自己攜帶杯子，不然我都不買手搖飲了。")
//        val mock5 = Share("Mack", "接雨水來喝", "2021.5.30", "南部最近缺水，我直接收集雨水來喝，節省水資源。")
//        val mockList1 = listOf(mock3, mock4, mock5)
//
//        adapter.submitList(mockList1)

        return binding.root
    }
}
