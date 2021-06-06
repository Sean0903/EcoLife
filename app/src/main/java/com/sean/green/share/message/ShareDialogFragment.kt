package com.sean.green.share.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sean.green.data.Share
import com.sean.green.databinding.ItemShareBinding

class ShareDialogFragment : Fragment() {


    private lateinit var binding: ItemShareBinding
//    private val viewModel: ShareViewModel by lazy {
//        ViewModelProvider(this).get(ShareViewModel::class.java)
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ItemShareBinding.inflate(inflater)
        binding.lifecycleOwner = this
//        binding.viewModel = viewModel

//        val adapter = ShareDialogAdapter()
//        binding.recycleViewShareDialog.adapter = adapter

        return binding.root
    }
}
