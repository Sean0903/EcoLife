package com.sean.green.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sean.green.databinding.DialogEventBinding
import com.sean.green.databinding.DialogSaveBinding

class DialogEventFragment : Fragment() {
    private lateinit var binding: DialogEventBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogEventBinding.inflate(inflater)

        binding.imageDialogEventCancel.setOnClickListener {
            binding.constraintLayoutDialogEvent.visibility = View.GONE
        }

        return binding.root
    }
}