package com.sean.green.share.toShare

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.sean.green.NavigationDirections
import com.sean.green.databinding.FragmentToShareBinding
import com.sean.green.ext.getVmFactory
import com.sean.green.login.UserManager

class ToShareFragment: Fragment() {

    private lateinit var binding: FragmentToShareBinding

    private val viewModel by viewModels<ToShareViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToShareBinding.inflate(inflater)

        binding.imageDialogShareCancel.setOnClickListener { view: View ->
            this.findNavController().popBackStack()
        }

        binding.editTextDialogShareExplain.doOnTextChanged { text, start, before, count ->
            viewModel.achievement.value = text.toString()
            Log.d("toShareFragment", "achievement = ${viewModel.achievement.value}")
        }

        binding.editTextDialogShareTime.doOnTextChanged { text, start, before, count ->
            viewModel.time.value = text.toString()
            Log.d("toShareFragment", "time = ${viewModel.time.value}")
        }

        binding.editTextDialogShareContent.doOnTextChanged { text, start, before, count ->
            viewModel.content.value = text.toString()
            Log.d("toShareFragment", "content = ${viewModel.content.value}")
        }

        binding.buttonDialogShareSend.setOnClickListener {

            if (viewModel.content.value.isNullOrBlank() ||
                viewModel.time.value.isNullOrBlank()||
                    viewModel.achievement.value.isNullOrBlank())  {
                Toast.makeText(context, "請在各欄輸入完整訊息", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.addSharingData2Firebase(UserManager.user.email,UserManager.user.image,UserManager.user.userName)
                Toast.makeText(context, "發送成功", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageDialogShareCancel.setOnClickListener {
            findNavController().navigate(
                NavigationDirections.navigateToHomeFragment(
//                FirebaseAuth.getInstance().currentUser!!.uid
            ))
        }

        return binding.root
    }
}