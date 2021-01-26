package com.gabrielcamargo.firebasechallenge.gamedetails.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.gabrielcamargo.firebasechallenge.R
import com.gabrielcamargo.firebasechallenge.gamedetails.viewModel.GameDetailsViewModel
import com.gabrielcamargo.firebasechallenge.games.model.GameModel

class GameDetailsFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = GameDetailsFragment()
    }

    private lateinit var viewModel: GameDetailsViewModel
    private lateinit var _gameModel: GameModel
    private lateinit var _view: View
    private lateinit var _navController: NavController
    private val args: GameDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _view = inflater.inflate(R.layout.game_details_fragment, container, false)
        return _view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _navController = Navigation.findNavController(_view)

        _gameModel = GameModel(
            args.gameId,
            args.gameName,
            args.gameDesc,
            args.gameYear,
            args.gameImg
        )

        setContent()
        bindEvents()
    }

    private fun setContent() {
        val imgGame = _view.findViewById<ImageView>(R.id.imgGame_gameDetailsFragment)
        val txtNameImg = _view.findViewById<TextView>(R.id.txtNameImgTitle_gameDetailsFragment)
        val txtName = _view.findViewById<TextView>(R.id.txtName_gameDetailsFragment)
        val txtYear = _view.findViewById<TextView>(R.id.txtYearValue_gameDetailsFragment)
        val txtDesc = _view.findViewById<TextView>(R.id.txtDesc_gameDetailsFragment)

        Glide.with(_view)
            .load(_gameModel.imgUrl)
            .centerCrop()
            .into(imgGame)

        txtNameImg.text = _gameModel.name
        txtName.text = _gameModel.name
        txtYear.text = _gameModel.createdAt.toString()
        txtDesc.text = _gameModel.description
    }

    private fun bindEvents() {
        val imgGame = _view.findViewById<ImageView>(R.id.imgBack_GameDetailsFragment)
        imgGame.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        p0?.let {
            when(it.id) {
                R.id.imgBack_GameDetailsFragment -> backToGameList()
            }
        }
    }

    private fun backToGameList() {
        _navController.navigate(R.id.gamesFragment)
    }

}