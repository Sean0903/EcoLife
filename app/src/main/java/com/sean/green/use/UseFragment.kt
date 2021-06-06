package com.sean.green.use

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.sean.green.NavigationDirections
import com.sean.green.R
import com.sean.green.databinding.FragmentUseBinding
import com.sean.green.ext.getVmFactory


class UseFragment: Fragment() {

    private lateinit var binding : FragmentUseBinding

    private val viewModel by viewModels<UseViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUseBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        viewModel.plastic.observe(viewLifecycleOwner, Observer {
            Log.i("useFragment","plastic = ${viewModel.plastic.value}")
        }
        )

        viewModel.power.observe(viewLifecycleOwner, Observer {
            Log.i("useFragment","power = ${viewModel.power.value}")
        }
        )

        viewModel.carbon.observe(viewLifecycleOwner, Observer {
            Log.i("useFragment","carbon = ${viewModel.carbon.value}")
        }
        )


        binding.imageUsePageInfo.setOnClickListener {

            var useDialog = Dialog(this.requireContext())
            val view = layoutInflater.inflate(R.layout.dialog_use, null)
            useDialog.setContentView(view)
            useDialog.show()

        }

        binding.buttonUseSave.setOnClickListener {
            viewModel.addUseData2Firebase()
        }

        binding.imageUsePageBackToHome.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToHomeFragment(
//                FirebaseAuth.getInstance().currentUser!!.uid
            ))
        }

        return binding.root
    }
}