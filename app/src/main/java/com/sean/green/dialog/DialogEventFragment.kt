package com.sean.green.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController

import com.sean.green.databinding.DialogEventBinding


class DialogEventFragment : DialogFragment() {
    private lateinit var binding: DialogEventBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogEventBinding.inflate(inflater)

        binding.imageDialogEventCancel.setOnClickListener { view: View ->

            //可能因為是同一個fragment所以不能跳轉
//            this.navigate(DialogEventFragmentDirections.actionDialogEventToEventFragment())
//            this.findNavController().popBackStack()
//            this.findNavController().navigateUp()
        }
            return binding.root
    }
}