package com.sean.green.save

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sean.green.databinding.FragmentHomeBinding
import com.sean.green.databinding.FragmentSaveBinding
import com.sean.green.home.HomeViewModel

class SaveFragment: Fragment() {

    private lateinit var binding : FragmentSaveBinding
    private val viewModel : SaveViewModel by lazy {
        ViewModelProvider(this).get(SaveViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSaveBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }
}