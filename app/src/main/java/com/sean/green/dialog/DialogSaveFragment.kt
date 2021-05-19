package com.sean.green.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sean.green.databinding.DialogSaveBinding


class DialogSaveFragment : Fragment() {
    private lateinit var binding: DialogSaveBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogSaveBinding.inflate(inflater)
        return binding.root
    }
}
