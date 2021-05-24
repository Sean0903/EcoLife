package com.sean.green.challenge

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sean.green.NavigationDirections
import com.sean.green.databinding.FragmentChallengeBinding
import com.sean.green.ext.getVmFactory



class ChallengeFragment: Fragment() {

//    private lateinit var binding : FragmentChallengeBinding

    private val viewModel by viewModels<ChallengeViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentChallengeBinding.inflate(inflater, container, false)


        viewModel.plastic.observe(viewLifecycleOwner, Observer {
            Log.i("challengeFragment","plastic = ${viewModel.plastic.value}")
        }
        )

        viewModel.power.observe(viewLifecycleOwner, Observer {
            Log.i("challengeFragment","power = ${viewModel.power.value}")
        }
        )

        viewModel.carbon.observe(viewLifecycleOwner, Observer {
            Log.i("challengeFragment","carbon = ${viewModel.carbon.value}")
        }
        )


        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.buttonChallengeSave.setOnClickListener {

            viewModel.addChallengeData2Firebase()
        }

        binding.imageUsePageBackToHome2.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToHomeFragment())
        }

        return binding.root
    }
}