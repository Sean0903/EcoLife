package com.sean.green.use

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sean.green.NavigationDirections
import com.sean.green.R
import com.sean.green.databinding.FragmentUseBinding
import com.sean.green.ext.getVmFactory
import com.sean.green.login.UserManager.user


class UseFragment : Fragment() {

    private lateinit var binding: FragmentUseBinding

    private val viewModel by viewModels<UseViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUseBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.imageUsePageInfo.setOnClickListener {

            var useDialog = Dialog(this.requireContext())
            val view = layoutInflater.inflate(R.layout.dialog_use, null)
            useDialog.setContentView(view)
            useDialog.show()

        }

        binding.imageUsePageBackToHome.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToHomeFragment())
        }

        binding.editTextUsePageContent.doOnTextChanged { text, start, before, count ->
            viewModel.content.value = text.toString()
            Log.d("saveFragment", "content = ${viewModel.content.value}")
        }

        binding.buttonUseSave.setOnClickListener {

            if (viewModel.plastic.value.isNullOrBlank() &&
                viewModel.power.value.isNullOrBlank() &&
                viewModel.carbon.value.isNullOrBlank()
            ) {
                Toast.makeText(context, "請輸入消耗", Toast.LENGTH_LONG).show()
            } else {
                viewModel.addUseData2Firebase(user.email)
                Toast.makeText(context, "已成功送出", Toast.LENGTH_LONG).show()
            }

            if (viewModel.content.value != null) {
                viewModel.addArticle2Firebase(user.email)
                Toast.makeText(context, "已成功送出", Toast.LENGTH_LONG).show()
            }
        }
        return binding.root
    }

}