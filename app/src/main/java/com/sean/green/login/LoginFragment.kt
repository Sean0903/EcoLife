package com.sean.green.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.sean.green.NavigationDirections
import com.sean.green.R
import com.sean.green.databinding.FragmentLoginBinding
import com.sean.green.ext.getVmFactory

/**
 * Demonstrate Firebase Authentication using a Google ID Token.
 */
class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding

    private lateinit var googleSignInClient: GoogleSignInClient

    private val viewModel by viewModels<LoginViewModel> { getVmFactory() }

    companion object {
        internal const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        FirebaseAuth.getInstance().currentUser?.let {
            viewModel.findUser(it, true)
        }

        viewModel.navigateToHome.observe(viewLifecycleOwner, Observer {
            it.let {
                findNavController().navigate(NavigationDirections.navigateToHomeFragment(
                    FirebaseAuth.getInstance().currentUser!!.uid
                ))
                viewModel.onSucceeded()
            }
        })

        viewModel.loginAttempt.observe(viewLifecycleOwner, Observer {
            it?.let {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.token_id))
                    .requestEmail()
                    .build()
                googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
                val signInIntent = googleSignInClient.signInIntent
                startActivityForResult(signInIntent, RC_SIGN_IN)
                viewModel.afterLogin()
            }
        })

        binding.signInButton.setOnClickListener {
            viewModel.loginGoogle()
        }

        return binding.root
    }

//    fun onClick(v: View) {
//        when (v.id) {
//            R.id.sign_in_button -> signIn()
//        }
//    }

    // [START onactivityresult]
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                viewModel.firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }
    // [END onactivityresult]

}