package com.sean.green.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.sean.green.databinding.DialogChallengeBinding

class DialogChallengeFragment: DialogFragment(){

    private lateinit var binding: DialogChallengeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DialogChallengeBinding.inflate(inflater)

        return binding.root
    }
}