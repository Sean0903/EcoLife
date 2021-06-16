package com.sean.green.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.sean.green.MainActivity
import com.sean.green.R
import com.sean.green.databinding.FragmentHomeBinding
import com.sean.green.ext.getVmFactory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
//    private val viewModel : HomeViewModel by lazy {
//        ViewModelProvider(this).get(HomeViewModel::class.java)
//    }

    private val viewModel by viewModels<HomeViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val adapter = HomeAdapter()
        binding.recyclerViewHome.adapter = adapter

        viewModel.articleDataForRecycleView.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)

            if (viewModel.articleDataForRecycleView.value.isNullOrEmpty()) {
                binding.imageHomeCard.visibility = View.VISIBLE
                binding.textHomeSlogan1.visibility = View.VISIBLE
                binding.textHomeSlogan2.visibility = View.VISIBLE
                binding.textHomeSlogan3.visibility = View.VISIBLE
            } else {
                binding.imageHomeCard.visibility = View.GONE
                binding.textHomeSlogan1.visibility = View.GONE
                binding.textHomeSlogan2.visibility = View.GONE
                binding.textHomeSlogan3.visibility = View.GONE
            }
        })

        binding.imageHomeSaveInfo.setOnClickListener {

            var saveDialog = Dialog(this.requireContext())
            val view = layoutInflater.inflate(R.layout.dialog_save, null)
            saveDialog.setContentView(view)
            saveDialog.show()

        }

        binding.imageHomeUseInfo.setOnClickListener {

            var useDialog = Dialog(this.requireContext())
            val view = layoutInflater.inflate(R.layout.dialog_use, null)
            useDialog.setContentView(view)
            useDialog.show()

        }

        binding.imageHomeChallengeInfo.setOnClickListener {

            var challengeDialog = Dialog(this.requireContext())
            val view = layoutInflater.inflate(R.layout.dialog_challenge, null)
            challengeDialog.setContentView(view)
            challengeDialog.show()

        }

//        viewModel.getTotalSaveNum(UserManager.user.email)
//        viewModel.getTotalUseNum(UserManager.user.email)
////        viewModel.getNowChallengeNum(UserManager.user.email)
//        viewModel.getArticleData(UserManager.user.email)

//        viewModel.saveNum.observe(viewLifecycleOwner, Observer {
//            viewModel.getNowChallengeNum(user.email)
//        })
//
//        viewModel.useNum.observe(viewLifecycleOwner, Observer {
//            viewModel.getNowChallengeNum(user.email)
//        })


//        viewModel.isCallDeleteAction.observe(viewLifecycleOwner, Observer {
//            if (it == true){
//                viewModel.getTotalUseNum(UserManager.user.email)
//                viewModel.getTotalSaveNum(UserManager.user.email)
//
//            }
//        })
//
//        viewModel.date.observe(viewLifecycleOwner, Observer { it ->
//
//            if (it != null){
//                viewModel.getTotalUseNum(UserManager.user.email)
//                viewModel.getTotalSaveNum(UserManager.user.email)
//            }
//        })



        (activity as MainActivity).dismissFabButton(true)

        return binding.root
    }


}

