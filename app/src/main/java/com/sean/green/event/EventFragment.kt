package com.sean.green.event

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sean.green.databinding.FragmentEventBinding
import com.sean.green.ext.getVmFactory

class EventFragment: Fragment() {

    private lateinit var binding : FragmentEventBinding
    private val viewModel by viewModels<EventViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.recyclerViewEvent.adapter = EventAdapter(viewModel,EventAdapter.OnClickListener {
            Log.d("test","eventMember= $it")
        })

        return binding.root
    }
}