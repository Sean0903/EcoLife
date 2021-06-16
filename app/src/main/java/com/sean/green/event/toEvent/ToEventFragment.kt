package com.sean.green.event.toEvent

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.sean.green.NavigationDirections
import com.sean.green.R
import com.sean.green.data.Event
import com.sean.green.databinding.FragmentToEventBinding
import com.sean.green.ext.getVmFactory
import com.sean.green.login.UserManager
import com.sean.green.share.toShare.ToShareViewModel
import io.grpc.InternalChannelz.id
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
            Log.d("toEventFragment", "introduction = ${viewModel.introduction.value}")
        }

        binding.editTextDialogEventTime.doOnTextChanged { text, start, before, count ->
            viewModel.time.value = text.toString()
            Log.d("toEventFragment", "time = ${viewModel.time.value}")
        }

        binding.editTextDialogEventLocation.doOnTextChanged { text, start, before, count ->
            viewModel.location.value = text.toString()
            Log.d("toEventFragment", "location = ${viewModel.location.value}")
        }

        binding.editTextDialogEventContent.doOnTextChanged { text, start, before, count ->
            viewModel.content.value = text.toString()
            Log.d("toEventFragment", "content = ${viewModel.content.value}")
        }

        binding.buttonDialogEventSend.setOnClickListener {
            viewModel.addEventData2Firebase(
                Event(),
                UserManager.user.email,
                UserManager.user.image,
                UserManager.user.userName)
        }

        binding.endDate.apply {
            minDate = System.currentTimeMillis()
            setOnDateChangedListener { _, year, month, date ->
                val format = SimpleDateFormat(getString(R.string.diary_record_date))
                viewModel.dueDate.value = format.parse("$date/${month + 1}/$year").time
                Log.d("endDate","minDate = $minDate")
                Log.d("endDate","format = $format")
                Log.d("endDate","viewModel.dueDate.value =${viewModel.dueDate.value}")
            }
        }

//        viewModel.dueDate.observe(viewLifecycleOwner, Observer {
//            viewModel.stampToDate(viewModel.dueDate.value!!)
//        })
            return binding.root
    }
}