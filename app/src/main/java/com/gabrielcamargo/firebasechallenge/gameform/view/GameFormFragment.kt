package com.gabrielcamargo.firebasechallenge.gameform.view

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.gabrielcamargo.firebasechallenge.R
import com.gabrielcamargo.firebasechallenge.gameform.viewmodel.GameFormViewModel
import com.gabrielcamargo.firebasechallenge.games.model.GameModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.pnikosis.materialishprogress.ProgressWheel
import java.lang.System.currentTimeMillis


class GameFormFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = GameFormFragment()
        const val CONTENT_REQUEST_CODE = 1
    }
    private lateinit var _auth: FirebaseAuth

    private lateinit var viewModel: GameFormViewModel
    private lateinit var _gameModel: GameModel
    private lateinit var _view: View
    private lateinit var database: DatabaseReference
    private val args: GameFormFragmentArgs by navArgs()
    private lateinit var _navController: NavController
    private var imageUri: Uri? = null
    private var imageUrlStorage: String = ""
    private lateinit var progressOverlay: FrameLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _view = inflater.inflate(R.layout.game_form_fragment, container, false)
        return _view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressOverlay = _view.findViewById<FrameLayout>(R.id.progress_overlay);

        _auth = Firebase.auth
        val currentUser = _auth.currentUser

        database = Firebase.database.reference.child("users")
            .child(currentUser!!.uid).child("games")

        database.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                dataSnapshot.children.forEach {
                    //"it" is the snapshot
                    val key: String = it.key.toString()
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                //do whatever you need
            }
        })

        viewModel = ViewModelProvider(this).get(GameFormViewModel::class.java)
        _navController = Navigation.findNavController(_view)

        _gameModel = GameModel(
            args.gameId ?: "",
            args.gameName ?: "",
            args.gameDescription ?: "",
            args.gameYear,
            args.gameImgUrl ?: ""
        )

        setData()
        bindEvents()
    }

    private fun setData() {
        if(_gameModel.id.isNotEmpty()) {
            val edtName = _view.findViewById<TextInputLayout>(R.id.edtName_gameFormFragment)
            edtName.editText?.setText(_gameModel.name)

            val edtCreatedAt = _view.findViewById<TextInputLayout>(R.id.edtCreatedAt_gameFormFragment)
            edtCreatedAt.editText?.setText(_gameModel.createdAt.toString())

            val edtDesc = _view.findViewById<TextInputLayout>(R.id.edtDescription_gameFormFragment)
            edtDesc.editText?.setText(_gameModel.description)

            val imgView = _view.findViewById<ImageView>(R.id.imgView_gameFormFragment)
            imgView.setPadding(8)

            if(_gameModel.imgUrl.isNotEmpty()) {
                imageUrlStorage = _gameModel.imgUrl

                Glide.with(_view)
                    .load(_gameModel.imgUrl)
                    .circleCrop()
                    .into(imgView)
            }
        }
    }

    private fun bindEvents() {
        val btnSave = _view.findViewById<Button>(R.id.btnSaveGame_gameFormFragment)
        btnSave.setOnClickListener(this)

        val imgView = _view.findViewById<ImageView>(R.id.imgView_gameFormFragment)
        imgView.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        p0?.let {
            when(it.id) {
                R.id.btnSaveGame_gameFormFragment -> saveGame()
                R.id.imgView_gameFormFragment -> searchImage()
            }
        }
    }

    private fun searchImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, CONTENT_REQUEST_CODE)
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
                if(_gameModel.id.isNotEmpty()) {
                    updateGame(
                        name,
                        desc,
                        createdAt,
                        imageUrlStorage
                    )
                } else {
                    insertGame(
                        name,
                        desc,
                        createdAt,
                        imageUrlStorage
                    )
                }
            }
        }
    }

    private fun insertGame(name: String, desc: String, createdAt: String, imgUrl: String) {
        val newGame = database.push()

        newGame.setValue(
            GameModel(
                newGame.key!!,
                name,
                desc,
                createdAt.toInt(),
                imgUrl
            )
        ).addOnSuccessListener {
            _navController.navigate(R.id.gamesFragment)
        }.addOnFailureListener {
            Snackbar.make(_view, "Falha ao cadastrar o jogo.", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun updateGame(name: String, desc: String, createdAt: String, imgUrl: String) {
        val uid = _gameModel.id

        database.child(uid).setValue(
            GameModel(
                uid,
                name,
                desc,
                createdAt.toInt(),
                imgUrl
            )
        ).addOnSuccessListener {
            _navController.navigate(R.id.gamesFragment)
        }.addOnFailureListener {
            Snackbar.make(_view, "Falha ao atualizar o jogo o jogo.", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == CONTENT_REQUEST_CODE && resultCode == RESULT_OK) {
            imageUri = data?.data

            sendImgToStorage()
        }
    }

    private fun sendImgToStorage() {
        imageUri?.run {
            val firebase = FirebaseStorage.getInstance()
            val storage = firebase.getReference("gameImages")

            val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(
                _view.context.contentResolver.getType(
                    this
                )
            )
            val fileReference = storage.child("${currentTimeMillis()}.${extension}")

            showOverlay()
            fileReference.putFile(this)
                .addOnSuccessListener {

                    fileReference.downloadUrl.addOnSuccessListener {
                        imageUrlStorage = it.toString()
                        Log.d("GAME_FORM_FRAGMENT", "Image upload: success - $imageUrlStorage")

                        val imgView = _view.findViewById<ImageView>(R.id.imgView_gameFormFragment)

                        imgView.setPadding(8)
                        Glide.with(_view)
                            .load(imageUrlStorage)
                            .circleCrop()
                            .into(imgView)

                    }.addOnFailureListener{
                        imageUrlStorage = ""
                        Log.d("GAME_FORM_FRAGMENT", "Image download: failure - ${it.message}")
                    }

                }
                .addOnFailureListener {
                    Log.d("GAME_FORM_FRAGMENT", "Image upload: error - ${it.message}")
                }
                .addOnCompleteListener{
                    hideOverlay()
                }
        }
    }

    fun hideOverlay() {
        progressOverlay.visibility = View.INVISIBLE
    }

    fun showOverlay() {
        progressOverlay.visibility = View.VISIBLE
    }
}