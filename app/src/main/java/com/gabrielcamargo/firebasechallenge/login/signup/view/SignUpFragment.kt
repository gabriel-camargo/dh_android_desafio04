package com.gabrielcamargo.firebasechallenge.login.signup.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.gabrielcamargo.firebasechallenge.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignUpFragment : Fragment(), View.OnClickListener {

    companion object {
        const val TAG = "SIGN_UP_FRAGMENT"
    }

    private lateinit var _view: View
    private lateinit var _navController: NavController
    private lateinit var _auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _view = inflater.inflate(R.layout.sign_up_fragment, container, false)
        return _view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _auth = Firebase.auth
        _navController = Navigation.findNavController(_view)

        bindEvents()
    }

    override fun onClick(v: View?) {
        v?.let {
            when(it.id) {
                R.id.btnCreateAccount_signUpFragment -> clickCreateAccount()
            }
        }
    }

    private fun bindEvents() {
        val btnCreateAccount = _view.findViewById<Button>(R.id.btnCreateAccount_signUpFragment)
        btnCreateAccount.setOnClickListener(this)
    }

    private fun clickCreateAccount() {
        val edtName = _view.findViewById<TextInputLayout>(R.id.edtName_signUpFragment)
        val name = edtName.editText?.text.toString().trim()

        val edtEmail = _view.findViewById<TextInputLayout>(R.id.edtEmail_signUpFragment)
        val email = edtEmail.editText?.text.toString().trim()

        val edtPassword = _view.findViewById<TextInputLayout>(R.id.edtPassword_signUpFragment)
        val password = edtPassword.editText?.text.toString().trim()

        val edtConfirmPassword = _view.findViewById<TextInputLayout>(R.id.edtPasswordConfirm_signUpFragment)
        val confirmPassword = edtConfirmPassword.editText?.text.toString().trim()

        edtName.error=null
        edtEmail.error=null
        edtPassword.error=null
        edtConfirmPassword.error=null

        when {
            name.isBlank() -> {
                edtName.error = "Preencha um nome válido!"
            }
            email.isBlank() -> {
                edtEmail.error = "Preencha um endereço de email."
            }
            password.isBlank() -> {
                edtPassword.error = "Preencha uma senha válida."
            }
            password != confirmPassword -> {
                edtPassword.error = "As senhas devem ser iguais."
                edtConfirmPassword.error = "As senhas devem ser iguais."
            }
            else -> {
                activity?.let { itActivity ->
                    _auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(itActivity) { task ->
                            if(task.isSuccessful) {
                                val profileUpdates = UserProfileChangeRequest.Builder()
                                    .setDisplayName(name).build()

                                _auth.currentUser!!.updateProfile(profileUpdates)

                                Toast.makeText(_view.context, "Usuário criado com sucesso! $name", Toast.LENGTH_SHORT).show()
                                _navController.navigate(R.id.mainActivity)
                            } else {
                                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                Snackbar.make(
                                    _view,
                                    "Criação de usuário falhou",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        }

    }
}