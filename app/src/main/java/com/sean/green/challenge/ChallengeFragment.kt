package com.sean.green.challenge

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sean.green.databinding.FragmentChallengeBinding

class ChallengeFragment: Fragment() {

    private lateinit var binding : FragmentChallengeBinding
    private val viewModel : ChallengeViewModel by lazy {
        ViewModelProvider(this).get(ChallengeViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChallengeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.editTextChallengePagePlastic.doOnTextChanged { text, start, before, count ->
            viewModel.plastic.value.toString()
            Log.d("sean", "viewModel.plastic.value = ${viewModel.plastic.value}")
        }

        binding.editTextChallengePagePower.doOnTextChanged { text, start, before, count ->
            viewModel.power.value.toString()
            Log.d("sean", "viewModel.power.value = ${viewModel.power.value}")
        }

        binding.editTextChallengePageCarbon.doOnTextChanged { text, start, before, count ->
            viewModel.carbon.value.toString()
            Log.d("sean", "viewModel.carbon.value = ${viewModel.carbon.value}")
        }

        return binding.root
    }
}