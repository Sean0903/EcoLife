package com.sean.green.use

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sean.green.databinding.FragmentHomeBinding
import com.sean.green.databinding.FragmentUseBinding
import com.sean.green.home.HomeViewModel

class UseFragment: Fragment() {

    private lateinit var binding : FragmentUseBinding
    private val viewModel : UseViewModel by lazy {
        ViewModelProvider(this).get(UseViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUseBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }
}