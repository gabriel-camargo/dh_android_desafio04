package com.gabrielcamargo.firebasechallenge.login.signin.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.gabrielcamargo.firebasechallenge.R
import com.gabrielcamargo.firebasechallenge.login.signin.viewmodel.SignInViewModel
import com.gabrielcamargo.firebasechallenge.login.signup.view.SignUpFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInFragment : Fragment(), View.OnClickListener {

    private lateinit var _view: View
    private lateinit var _viewModel: SignInViewModel
    private lateinit var _navController: NavController
    private lateinit var _auth: FirebaseAuth


    companion object {
        fun newInstance() = SignInFragment()
        private const val TAG = "SIGN_IN_FRAGMENT"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _view = inflater.inflate(R.layout.sign_in_fragment, container, false)
        return _view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _auth = Firebase.auth
        _viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        _navController = Navigation.findNavController(_view)

        bindEvents()
    }

    override fun onClick(v: View?) {
        v?.let {
            when(it.id) {
                R.id.btnCreatAccount_signInFragment -> goToSignUpFragment()
                R.id.btnLogIn_signInFragment -> signIn()
            }
        }
    }

    private fun bindEvents() {
        val btnCreateAccount = _view.findViewById<Button>(R.id.btnCreatAccount_signInFragment)
        btnCreateAccount.setOnClickListener(this)

        val btnLogIn = _view.findViewById<Button>(R.id.btnLogIn_signInFragment)
        btnLogIn.setOnClickListener(this)
    }

    private fun goToSignUpFragment() {
        _navController.navigate(R.id.signUpFragment)
    }

    private fun signIn() {
        val edtEmail = _view.findViewById<TextInputLayout>(R.id.edtEmail_signInFragment)
        val email = edtEmail.editText?.text.toString().trim()

        val edtPassword = _view.findViewById<TextInputLayout>(R.id.edtPassword_signInFragment)
        val password = edtPassword.editText?.text.toString().trim()

        edtEmail.error=null
        edtPassword.error=null

        when {
            email.isBlank() -> {
                edtEmail.error = "Preencha um endereço de email."
            }
            password.isBlank() -> {
                edtPassword.error = "Preencha uma senha válida."
            }
            else -> {
                activity?.let { itActivity ->
                    _auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(itActivity) { task ->
                            if(task.isSuccessful) {
                                val user = _auth.currentUser
                                Log.d(TAG, "Logado com sucesso! ${user?.displayName}")
                                _navController.navigate(R.id.mainActivity)
                            } else {
                                Log.w(TAG, "login:failure", task.exception)
                                Snackbar.make(
                                    _view,
                                    "Email ou senha incorretas!",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                        }
                }
            }
        }
    }
}