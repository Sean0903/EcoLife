package com.sean.green.share

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.sean.green.MainActivity
import com.sean.green.NavigationDirections
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

//        val adapter = ShareAdapter(viewModel)
        binding.recyclerViewShare.adapter = ShareAdapter(viewModel, ShareAdapter.OnClickListener {
            Log.d("test", "share= $it")
        })

//        viewModel.shareDataForRecycleView.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                Log.d("shareFragment", "it = ${it}")
//                adapter.notifyDataSetChanged()
//                adapter.submitList(it)
//            }
//        })

        var count = 0

        viewModel.saveDataSevenDays.observe(viewLifecycleOwner, Observer {
            viewModel.setSaveDataForChart()

            count++
            if (count == 7) {
                binding.lottieAnimationView.visibility = View.GONE

            }
        })

        binding.fab.setOnClickListener {
            findNavController().navigate(NavigationDirections.actionShareFragmentToToShareFragment())
        }

//        viewModel.userImage.observe(viewLifecycleOwner, Observer {
//            Log.d("userImage","userImage = ${viewModel.userImage.value}")
//        })

        binding.lottieAnimationView.repeatCount = -1
        // start lottie
        binding.lottieAnimationView.playAnimation()

        (activity as MainActivity).dismissFabButton(true)

        return binding.root
    }
}