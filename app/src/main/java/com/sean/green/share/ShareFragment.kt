package com.sean.green.share

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sean.green.MainActivity
import com.sean.green.R
import com.sean.green.databinding.FragmentSaveBinding
import com.sean.green.databinding.FragmentShareBinding

class ShareFragment: Fragment() {

    private lateinit var binding : FragmentShareBinding
    private val viewModel : ShareViewModel by lazy {
        ViewModelProvider(this).get(ShareViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShareBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.fab.setOnClickListener {
            var dialogShare = Dialog(this.requireContext())
            val view = layoutInflater.inflate(R.layout.dialog_share, null)
            dialogShare.setContentView(view)
            dialogShare.show()
        }



        (activity as MainActivity).dismissFabButton(false)

        return binding.root
    }
}