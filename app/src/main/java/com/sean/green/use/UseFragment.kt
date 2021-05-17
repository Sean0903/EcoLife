package com.sean.green.use

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sean.green.R
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

        binding.editTextUsePagePlastic.doOnTextChanged { text, start, before, count ->
            viewModel.plastic.value.toString()
            Log.d("sean", "viewModel.plastic.value = ${viewModel.plastic.value}")
        }

        binding.editTextUsePagePower.doOnTextChanged { text, start, before, count ->
            viewModel.power.value.toString()
            Log.d("sean", "viewModel.power.value = ${viewModel.power.value}")
        }

        binding.editTextUsePageCarbon.doOnTextChanged { text, start, before, count ->
            viewModel.carbon.value.toString()
            Log.d("sean", "viewModel.carbon.value = ${viewModel.carbon.value}")
        }

        binding.imageUsePageInfo.setOnClickListener {

            var useDialog = Dialog(this.requireContext())
            val view = layoutInflater.inflate(R.layout.dialog_use, null)
            useDialog.setContentView(view)
            useDialog.show()

        }

        return binding.root
    }
}