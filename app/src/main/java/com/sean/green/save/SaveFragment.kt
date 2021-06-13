package com.sean.green.save

import android.app.Dialog
import android.os.Bundle
import android.os.UserManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.UserManagerCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sean.green.NavigationDirections
import com.sean.green.R
import com.sean.green.databinding.FragmentSaveBinding
import com.sean.green.ext.getVmFactory
import com.sean.green.login.UserManager.user


class SaveFragment : Fragment() {

    var db = FirebaseFirestore.getInstance()

//    private lateinit var binding : FragmentSaveBinding

    private val viewModel by viewModels<SaveViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentSaveBinding.inflate(inflater, container, false)

        viewModel.plastic.observe(viewLifecycleOwner, Observer {
            Log.i("saveFragment", "plastic = ${viewModel.plastic.value}")
        }
        )

        viewModel.power.observe(viewLifecycleOwner, Observer {
            Log.i("saveFragment", "power = ${viewModel.power.value}")
        }
        )

        viewModel.carbon.observe(viewLifecycleOwner, Observer {
            Log.i("saveFragment", "carbon = ${viewModel.carbon.value}")
        }
        )

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.imageSavePageInfo.setOnClickListener {

            var saveDialog = Dialog(this.requireContext())
            val view = layoutInflater.inflate(R.layout.dialog_save, null)
            saveDialog.setContentView(view)
            saveDialog.show()

        }

        binding.buttonSavePage.setOnClickListener {

            if (viewModel.plastic.value.isNullOrBlank() &&
                viewModel.power.value.isNullOrBlank() &&
                viewModel.carbon.value.isNullOrBlank()) {
                Toast.makeText(context, "請輸入成果", Toast.LENGTH_LONG).show()
            } else {
                viewModel.addSaveData2Firebase(user.email)
            }

            if (viewModel.content.value != null) {
                viewModel.addArticle2Firebase(user.email)
            }
        }

        binding.imageSavePageBackToHome.setOnClickListener {
            findNavController().navigate(
                NavigationDirections.navigateToHomeFragment(
                )
            )
        }

        binding.editTextSavePageContent.doOnTextChanged { text, start, before, count ->
            viewModel.content.value = text.toString()
            Log.d("saveFragment", "content = ${viewModel.content.value}")
        }

        return binding.root
    }
}


//        viewModel.navigateToHome.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                findNavController().navigate(NavigationDirections.navigateToHomeFragment(it))
//                viewModel.onDetailNavigated()
//            }
//        })
//
//        binding.imageSavePageBackToHome.setOnClickListener {it:Save!
//            viewModel.navigateToHome(it)
//        }
