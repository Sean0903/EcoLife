package com.sean.green.challenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sean.green.calendar.CalendarViewModel
import com.sean.green.databinding.FragmentCalendarBinding

//class ChallengeFragment: Fragment {

//    private lateinit var binding : FragmentCalendarBinding
//    private val viewModel : CalendarViewModel by lazy {
//        ViewModelProvider(this).get(CalendarViewModel::class.java)
//    }
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentCalendarBinding.inflate(inflater)
//        binding.lifecycleOwner = this
//        binding.viewModel = viewModel
//        return binding.root
//    }
//}