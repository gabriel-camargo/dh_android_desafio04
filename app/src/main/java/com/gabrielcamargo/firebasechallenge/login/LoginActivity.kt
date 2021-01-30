package com.gabrielcamargo.firebasechallenge.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gabrielcamargo.firebasechallenge.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Firebase.auth.signOut()
    }
}