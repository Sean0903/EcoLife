package com.sean.green.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sean.green.databinding.DialogShareBinding

class DialogShareFragment: Fragment() {

    private lateinit var binding: DialogShareBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogShareBinding.inflate(inflater)
        return binding.root
    }
}