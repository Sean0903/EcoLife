package com.sean.green.challenge

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.sean.green.NavigationDirections
import com.sean.green.R
import com.sean.green.databinding.FragmentChallengeBinding
import com.sean.green.ext.getVmFactory
import com.sean.green.login.UserManager


class ChallengeFragment : Fragment() {

    private lateinit var binding: FragmentChallengeBinding

    private val viewModel by viewModels<ChallengeViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChallengeBinding.inflate(inflater, container, false)

        viewModel.plastic.observe(viewLifecycleOwner, Observer {
            Log.i("challengeFragment", "plastic = ${viewModel.plastic.value}")
        }
        )

        viewModel.power.observe(viewLifecycleOwner, Observer {
            Log.i("challengeFragment", "power = ${viewModel.power.value}")
        }
        )

        viewModel.carbon.observe(viewLifecycleOwner, Observer {
            Log.i("challengeFragment", "carbon = ${viewModel.carbon.value}")
        }
        )


        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.imageUsePageBackToHome.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToHomeFragment())
        }

        binding.imageChallengePageInfo.setOnClickListener {

            var saveDialog = Dialog(this.requireContext())
            val view = layoutInflater.inflate(R.layout.dialog_challenge, null)
            saveDialog.setContentView(view)
            saveDialog.show()

        }

        binding.editTextSavePageContent.doOnTextChanged { text, start, before, count ->
            viewModel.content.value = text.toString()
            Log.d("saveFragment", "content = ${viewModel.content.value}")
        }

        binding.buttonChallengeSave.setOnClickListener {

            if (viewModel.plastic.value.isNullOrBlank() &&
                viewModel.power.value.isNullOrBlank() &&
                viewModel.carbon.value.isNullOrBlank()) {
                Toast.makeText(context, "請輸入挑戰", Toast.LENGTH_LONG).show()
            } else {
                viewModel.addChallengeData2Firebase(UserManager.user.email)
                Toast.makeText(context, "已成功送出", Toast.LENGTH_LONG).show()
            }

            if (viewModel.content.value != null) {
                viewModel.addArticle2Firebase(UserManager.user.email)
                Toast.makeText(context, "已成功送出", Toast.LENGTH_LONG).show()
            }
        }


        return binding.root
    }
}