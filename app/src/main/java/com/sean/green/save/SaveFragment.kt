package com.sean.green.save

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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.sean.green.NavigationDirections
import com.sean.green.R
import com.sean.green.databinding.FragmentSaveBinding
import com.sean.green.ext.getVmFactory


class SaveFragment: Fragment() {

        var db = FirebaseFirestore.getInstance()

    private lateinit var binding : FragmentSaveBinding
//    private val viewModel : SaveViewModel by lazy {
//        ViewModelProvider(this).get(SaveViewModel::class.java)
//    }

    private val viewModel by viewModels<SaveViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        val application = requireNotNull(activity).application
//
//        val save = SaveFragmentArgs.fromBundle(requireArguments()).addToHomeFragment
//
//        val viewModelFactory = SaveViewModelFactory(save, application)
//
//        val viewModel = ViewModelProvider(this,viewModelFactory).get(SaveViewModel::class.java)

        viewModel.plastic.observe(viewLifecycleOwner, Observer {
            Log.i("checkSaveData","plastic = ${viewModel.plastic.value}")
        }
        )

        viewModel.power.observe(viewLifecycleOwner, Observer {
            Log.i("checkSaveData","power = ${viewModel.power.value}")
        }
        )

        viewModel.carbon.observe(viewLifecycleOwner, Observer {
            Log.i("checkSaveData","carbon = ${viewModel.carbon.value}")
        }
        )

        val binding = FragmentSaveBinding.inflate(inflater, container, false)

//        val viewModel by viewModels<SaveViewModel> { getVmFactory(SaveFragmentArgs.fromBundle(requireArguments()).saveKey) }

        binding.lifecycleOwner = this

        binding.viewModel = viewModel



        binding.imageSavePageInfo.setOnClickListener {

            var saveDialog = Dialog(this.requireContext())
            val view = layoutInflater.inflate(R.layout.dialog_save, null)
            saveDialog.setContentView(view)
            saveDialog.show()

        }


        binding.buttonSavePage.setOnClickListener {
//                viewModel.addSaveData2fire()
            viewModel.addSaveData2Firebase()
            }

        binding.imageSavePageBackToHome.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToHomeFragment())
        }



        return binding.root
    }

        private fun send() {
        //測試
        val washingtonRef =
            db.collection("green").document("2021")
        washingtonRef.update("plastic", FieldValue.arrayUnion(viewModel.plastic.value))
        washingtonRef.update("power", FieldValue.arrayUnion(viewModel.power.value))
        washingtonRef.update("carbon", FieldValue.arrayUnion(viewModel.carbon.value))
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
