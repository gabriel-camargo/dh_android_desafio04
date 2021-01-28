package com.gabrielcamargo.firebasechallenge.gameform.view

import android.content.Intent
import android.media.Image
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.view.setPadding
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.gabrielcamargo.firebasechallenge.gameform.viewmodel.GameFormViewModel
import com.gabrielcamargo.firebasechallenge.R
import com.gabrielcamargo.firebasechallenge.games.model.GameModel
import com.gabrielcamargo.firebasechallenge.login.LoginActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class GameFormFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = GameFormFragment()
    }
    private lateinit var _auth: FirebaseAuth

    private lateinit var viewModel: GameFormViewModel
    private lateinit var _gameModel: GameModel
    private lateinit var _view: View
    private lateinit var database: DatabaseReference
    private val args: GameFormFragmentArgs by navArgs()
    private lateinit var _navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _view = inflater.inflate(R.layout.game_form_fragment, container, false)
        return _view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _auth = Firebase.auth
        val currentUser = _auth.currentUser

        database = Firebase.database.reference.child("users")
            .child(currentUser!!.uid).child("games")

        viewModel = ViewModelProvider(this).get(GameFormViewModel::class.java)
        _navController = Navigation.findNavController(_view)

        _gameModel = GameModel(
            args.gameId,
            args.gameName ?: "",
            args.gameDescription ?: "",
            args.gameYear,
            args.gameImgUrl ?: ""
        )

        setData()
        bindEvents()
    }

    private fun setData() {
        if(_gameModel.id >= 1) {
            val edtName = _view.findViewById<TextInputLayout>(R.id.edtName_gameFormFragment)
            edtName.editText?.setText(_gameModel.name)

            val edtCreatedAt = _view.findViewById<TextInputLayout>(R.id.edtCreatedAt_gameFormFragment)
            edtCreatedAt.editText?.setText(_gameModel.createdAt.toString())

            val edtDesc = _view.findViewById<TextInputLayout>(R.id.edtDescription_gameFormFragment)
            edtDesc.editText?.setText(_gameModel.description)

            val imgView = _view.findViewById<ImageView>(R.id.imgView_gameFormFragment)
            imgView.setPadding(8)

            Glide.with(_view)
                .load(_gameModel.imgUrl)
                .circleCrop()
                .into(imgView)
        }
    }

    private fun bindEvents() {
        val btnSave = _view.findViewById<Button>(R.id.btnSaveGame_gameFormFragment)
        btnSave.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        p0?.let {
            when(it.id) {
                R.id.btnSaveGame_gameFormFragment -> saveGame()
            }
        }
    }

    private fun saveGame() {

        val edtName = _view.findViewById<TextInputLayout>(R.id.edtName_gameFormFragment)
        val name = edtName.editText?.text.toString().trim()

        val edtCreatedAt = _view.findViewById<TextInputLayout>(R.id.edtCreatedAt_gameFormFragment)
        val createdAt = edtCreatedAt.editText?.text.toString().trim()

        val edtDesc = _view.findViewById<TextInputLayout>(R.id.edtDescription_gameFormFragment)
        val desc = edtDesc.editText?.text.toString().trim()

        val imgView = _view.findViewById<ImageView>(R.id.imgView_gameFormFragment)

        edtName.error = null
        edtCreatedAt.error = null
        edtDesc.error = null

        when {
            name.isBlank() -> {
                edtName.error = "Informe o nome do game"
            }
            createdAt.isBlank() -> {
                edtCreatedAt.error = "Informe o nome do game"
            }
            desc.isBlank() -> {
                edtDesc.error = "Informe o nome do game"
            }
            else -> {
                database.push().setValue(GameModel(
                    1,
                    name,
                    desc,
                    createdAt.toInt(),
                    "https://s1.gaming-cdn.com/images/products/2310/271x377/cuphead-cover.jpg"
                )).addOnSuccessListener {
                    _navController.navigate(R.id.gamesFragment)
                }.addOnFailureListener {
                    Snackbar.make(_view, "Falha ao cadastrar o jogo.", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}