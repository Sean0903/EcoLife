package com.sean.green.share

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.sean.green.databinding.FragmentShareBinding
import com.sean.green.ext.getVmFactory

class ShareFragment: Fragment() {

    private lateinit var binding : FragmentShareBinding
    private val viewModel by viewModels<ShareViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShareBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.recyclerViewShare.adapter = ShareAdapter(viewModel, ShareAdapter.OnClickListener {
            Log.d("test", "share= $it")
        })

        var count = 0

        viewModel.saveDataSevenDays.observe(viewLifecycleOwner, Observer {
            viewModel.setSaveDataForChart()

            count++
            if (count == 7) {
                binding.lottieShare.visibility = View.GONE
            }
        })

        binding.lottieShare.repeatCount = -1
        binding.lottieShare.playAnimation()

        return binding.root
    }
}