package com.sean.green.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sean.green.community.CommunityViewModel
import com.sean.green.data.Save
import com.sean.green.databinding.FragmentCommunityBinding
import com.sean.green.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private val viewModel : HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }
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

        val mock = Save("1","2","3","突然就封城了，好可怕",1341)
        val mock2 = Save()
        val mockList = listOf( mock, mock2,mock, mock2)

        adapter.submitList(mockList)

        return binding.root
    }
}