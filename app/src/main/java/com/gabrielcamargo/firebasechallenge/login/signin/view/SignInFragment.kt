package com.gabrielcamargo.firebasechallenge.login.signin.view

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
import com.gabrielcamargo.firebasechallenge.login.signin.viewmodel.SignInViewModel

class SignInFragment : Fragment(), View.OnClickListener {

    private lateinit var _view: View
    private lateinit var _viewModel: SignInViewModel
    private lateinit var _navController: NavController


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
        _navController.navigate(R.id.mainActivity)
    }
}