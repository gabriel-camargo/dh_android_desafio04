package com.gabrielcamargo.firebasechallenge.login.signup.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.gabrielcamargo.firebasechallenge.R
import com.gabrielcamargo.firebasechallenge.login.signin.view.SignInViewModel
import com.gabrielcamargo.firebasechallenge.login.signup.viewmodel.SignUpViewModel

class SignUpFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private lateinit var viewModel: SignUpViewModel
    private lateinit var _view: View
    private lateinit var _navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _view = inflater.inflate(R.layout.sign_up_fragment, container, false)
        return _view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        _navController = Navigation.findNavController(_view)

        bindEvents()
    }

    override fun onClick(v: View?) {
        v?.let {
            when(it.id) {
                R.id.btnCreateAccount_signUpFragment -> goToSignInFragment()
            }
        }
    }

    private fun bindEvents() {
        val btnCreateAccount = _view.findViewById<Button>(R.id.btnCreateAccount_signUpFragment)
        btnCreateAccount.setOnClickListener(this)
    }

    private fun goToSignInFragment() {
        _navController.navigate(R.id.signInFragment)
    }

}