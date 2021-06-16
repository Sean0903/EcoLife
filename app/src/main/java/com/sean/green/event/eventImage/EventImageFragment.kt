package com.sean.green.event.eventImage
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import com.sean.green.databinding.ItemEventBinding
//import com.sean.green.event.EventViewModel
//import com.sean.green.ext.getVmFactory
//
//class EventImageFragment: Fragment() {
//
//    private lateinit var binding : ItemEventBinding
//    private val viewModel by viewModels<EventViewModel> { getVmFactory() }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = ItemEventBinding.inflate(inflater)
//
//        binding.lifecycleOwner = this
//
//        binding.viewModel = viewModel
//
//        binding.recyclerViewEventImage.adapter = EventImageAdapter()
//
//        viewModel.eventDataForRecycleView.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                Log.d("eventFragment", "it = ${it}")
//                adapter.notifyDataSetChanged()
//                adapter.submitList(it)
//            }
//        })
//
//        return binding.root
//    }
//}