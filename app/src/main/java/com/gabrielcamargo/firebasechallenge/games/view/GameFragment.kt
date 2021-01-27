package com.gabrielcamargo.firebasechallenge.games.view

import android.os.Bundle
import android.service.controls.actions.FloatAction
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gabrielcamargo.firebasechallenge.R
import com.gabrielcamargo.firebasechallenge.gamedetails.view.GameDetailsFragmentDirections
import com.gabrielcamargo.firebasechallenge.games.model.GameModel
import com.gabrielcamargo.firebasechallenge.games.repository.GameRepository
import com.gabrielcamargo.firebasechallenge.games.viewmodel.GameViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class GameFragment : Fragment(), View.OnClickListener {

    private lateinit var _viewModel: GameViewModel
    private lateinit var _view: View
    private lateinit var _navController: NavController

    companion object {
        fun newInstance() = GameFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _view =  inflater.inflate(R.layout.game_fragment, container, false)
        return _view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _navController = Navigation.findNavController(_view)
        _viewModel = ViewModelProvider(
            this,
            GameViewModel.GameViewModelFactory(GameRepository(_view.context))
        ).get(GameViewModel::class.java)

        _viewModel.games.observe(viewLifecycleOwner, Observer {
            createList(it)
        })

        _viewModel.getGames()

        bindEvents()
    }

    private fun createList(games: List<GameModel>) {
        val viewManager = GridLayoutManager(_view.context, 2)
        val recyclerView = _view.findViewById<RecyclerView>(R.id.recyclerViewGames)
        val viewAdapter = GameAdapter(games) {
            val action = GameFragmentDirections.actionGamesFragmentToGameDetailsFragment(
                it.imgUrl,
                it.name,
                it.description,
                it.createdAt,
                it.id
            )
            _view.findNavController().navigate(action)
        }

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun bindEvents() {
        val btnCreate = _view.findViewById<FloatingActionButton>(R.id.btnCreateGame_gameFragment)
        btnCreate.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        p0?.let {
            when(it.id) {
                R.id.btnCreateGame_gameFragment -> goToGameCreateForm()
            }
        }
    }

    private fun goToGameCreateForm() {

        val action = GameFragmentDirections.actionGamesFragmentToGameFormFragment(
            -1,
            "",
            -1,
            "",
            ""
        )

        _navController.navigate(action)
    }
}