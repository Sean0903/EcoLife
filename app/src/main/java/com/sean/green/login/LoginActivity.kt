package com.sean.green.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.sean.green.MainActivity
import com.sean.green.R
import com.sean.green.data.User
import com.sean.green.ext.getVmFactory
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Demonstrate Firebase Authentication using a Google ID Token.
 */
class LoginActivity : AppCompatActivity() {

//    lateinit var binding: FragmentLoginBinding

    private val viewModel by viewModels<LoginViewModel> { getVmFactory() }
    private var auth: FirebaseAuth? = null
    private var googleSignInClient: GoogleSignInClient? = null
    private var GOOGLE_LOGIN_CODE = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        google_sign_in_button.setOnClickListener {
            //First step
            googleLogin()
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.token_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)


        viewModel.firebaseUser.observe(this, Observer {
            it?.let {
                moveMainPage(it)
            }
        })

    }

    override fun onStart() {
        super.onStart()
        moveMainPage(auth?.currentUser)
    }

    private fun googleLogin() {
        val signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, GOOGLE_LOGIN_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_LOGIN_CODE) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result != null) {
                if (result.isSuccess) {
                    val account = result.signInAccount
                    //Second step
                    viewModel.loginAuth(account)
                }
            }
        }
    }


    private fun moveMainPage(user: FirebaseUser?) {
        if (user != null) {
            val currentUser = User(image = user.photoUrl.toString(),
                email = user.email.toString(),
                userName  = user.displayName.toString())

            UserManager.user = currentUser
            viewModel.postUser(currentUser)

            Log.d("user","user = ${UserManager.user}")

            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(0, android.R.anim.fade_out)
            finish()
        }
    }

}