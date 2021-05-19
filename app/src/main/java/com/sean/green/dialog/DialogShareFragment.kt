package com.sean.green.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sean.green.databinding.DialogShareBinding

class DialogShareFragment: Fragment() {

    private lateinit var binding: DialogShareBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogShareBinding.inflate(inflater)

        binding.imageDialogShareCancel.setOnClickListener { view: View ->
            this.findNavController().popBackStack()
        }

        return binding.root
    }
}