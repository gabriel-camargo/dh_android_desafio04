package com.gabrielcamargo.firebasechallenge.games.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gabrielcamargo.firebasechallenge.R
import com.gabrielcamargo.firebasechallenge.games.model.GameModel
import com.gabrielcamargo.firebasechallenge.games.repository.GameRepository
import com.gabrielcamargo.firebasechallenge.games.viewmodel.GameViewModel

class GameFragment : Fragment() {

    private lateinit var _viewModel: GameViewModel
    private lateinit var _view: View
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

        _viewModel = ViewModelProvider(
            this,
            GameViewModel.GameViewModelFactory(GameRepository(_view.context))
        ).get(GameViewModel::class.java)

        _viewModel.games.observe(viewLifecycleOwner, Observer {
            createList(it)
        })

        _viewModel.getGames()
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
}