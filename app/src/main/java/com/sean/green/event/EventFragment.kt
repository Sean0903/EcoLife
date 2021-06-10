package com.sean.green.event

import android.app.Dialog
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
import com.sean.green.MainActivity
import com.sean.green.NavigationDirections
import com.sean.green.R
import com.sean.green.data.Event
import com.sean.green.databinding.FragmentEventBinding
import com.sean.green.ext.getVmFactory
import com.sean.green.share.ShareViewModel

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

        val adapter = EventAdapter()
        binding.recyclerViewEvent.adapter = adapter

        viewModel.eventDataForRecycleView.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("eventFragment", "it = ${it}")
                adapter.notifyDataSetChanged()
                adapter.submitList(it)
            }
        })

        binding.fab.setOnClickListener {
            findNavController().navigate(NavigationDirections.actionEventFragmentToToEventFragment())

        }

        (activity as MainActivity).dismissFabButton(true)

        return binding.root
    }
}