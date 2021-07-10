package com.sean.green.event.toEvent

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sean.green.NavigationDirections
import com.sean.green.R
import com.sean.green.data.Event
import com.sean.green.databinding.FragmentToEventBinding
import com.sean.green.ext.getVmFactory
import com.sean.green.login.UserManager
import java.text.SimpleDateFormat


class ToEventFragment : Fragment() {

    private lateinit var binding: FragmentToEventBinding

    private val viewModel by viewModels<ToEventViewModel> { getVmFactory() }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToEventBinding.inflate(inflater)

        binding.imageDialogEventCancel.setOnClickListener {
            findNavController().navigate(
                NavigationDirections.navigateToHomeFragment(
                ))
        }

        binding.editTextDialogEventExplain.doOnTextChanged { text, start, before, count ->
            viewModel.introduction.value = text.toString()
        }

        binding.editTextDialogEventTime.doOnTextChanged { text, start, before, count ->
            viewModel.time.value = text.toString()
        }

        binding.editTextDialogEventLocation.doOnTextChanged { text, start, before, count ->
            viewModel.location.value = text.toString()
        }

        binding.editTextDialogEventContent.doOnTextChanged { text, start, before, count ->
            viewModel.content.value = text.toString()
        }

        binding.buttonDialogEventSend.setOnClickListener {
            viewModel.addEventData2Firebase(
                Event(),
                UserManager.user.email,
                UserManager.user.image,
                UserManager.user.userName)
            findNavController().navigate(NavigationDirections.navigateToHomeFragment())

        }

        binding.datePickerDialogEvent.apply {
            minDate = System.currentTimeMillis()
            setOnDateChangedListener { _, year, month, date ->
                val format = SimpleDateFormat(getString(R.string.diary_record_date))
                viewModel.dueDate.value = format.parse("$date/${month + 1}/$year").time
            }
        }

            return binding.root
    }
}