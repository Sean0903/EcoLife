package com.sean.green.save

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sean.green.databinding.FragmentSaveBinding


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

        binding = FragmentSaveBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.editTextSavePagePlastic.doOnTextChanged { text, start, before, count ->
            viewModel.plastic.value.toString()
            Log.d("sean", "viewModel.plastic.value = ${viewModel.plastic.value}")
        }

        binding.editTextSavePagePower.doOnTextChanged { text, start, before, count ->
            viewModel.power.value.toString()
            Log.d("sean", "viewModel.power.value = ${viewModel.power.value}")
        }

        binding.editTextSavePageCarbon.doOnTextChanged { text, start, before, count ->
            viewModel.carbon.value.toString()
            Log.d("sean", "viewModel.carbon.value = ${viewModel.carbon.value}")
        }

        return binding.root
    }
}