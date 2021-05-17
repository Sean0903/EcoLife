package com.sean.green.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sean.green.R
import com.sean.green.data.Save
import com.sean.green.databinding.FragmentHomeBinding
import com.sean.green.ext.getVmFactory

class HomeFragment: Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private val viewModel : HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

//    private val viewModel by viewModels<HomeViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val adapter = HomeAdapter()
        binding.recyclerViewHome.adapter = adapter

        val mock = Save("1","2","3","突然就封城了，好可怕")
        val mock2 = Save()
        val mockList = listOf( mock, mock2,mock, mock2)

        adapter.submitList(mockList)

        viewModel.dataSaveFromFirebase.observe(viewLifecycleOwner, Observer {
//            viewModel.getAndSetSaveToday()
        })

//        viewModel.save.observe(viewLifecycleOwner, Observer {
//            viewModel.getDocument()
//        })

        binding.imageHomeSaveInfo.setOnClickListener {

            var saveDialog = Dialog(this.requireContext())
            val view = layoutInflater.inflate(R.layout.dialog_save, null)
            saveDialog.setContentView(view)
            saveDialog.show()

        }

        binding.imageHomeUseInfo.setOnClickListener {

            var useDialog = Dialog(this.requireContext())
            val view = layoutInflater.inflate(R.layout.dialog_use, null)
            useDialog.setContentView(view)
            useDialog.show()

        }


        return binding.root
    }


}