package com.gabrielcamargo.firebasechallenge.gameform.view

import android.media.Image
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.setPadding
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.gabrielcamargo.firebasechallenge.gameform.viewmodel.GameFormViewModel
import com.gabrielcamargo.firebasechallenge.R
import com.gabrielcamargo.firebasechallenge.games.model.GameModel
import com.google.android.material.textfield.TextInputLayout

class GameFormFragment : Fragment() {

    companion object {
        fun newInstance() = GameFormFragment()
    }

    private lateinit var viewModel: GameFormViewModel
    private lateinit var _gameModel: GameModel
    private lateinit var _view: View
    private val args: GameFormFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _view = inflater.inflate(R.layout.game_form_fragment, container, false)
        return _view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(GameFormViewModel::class.java)

        _gameModel = GameModel(
            args.gameId,
            args.gameName ?: "",
            args.gameDescription ?: "",
            args.gameYear,
            args.gameImgUrl ?: ""
        )

        setData()
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
}