package com.gabrielcamargo.firebasechallenge.games.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gabrielcamargo.firebasechallenge.games.model.GameModel
import com.gabrielcamargo.firebasechallenge.games.repository.GameRepository

class GameViewModel(
    private val repository: GameRepository
) : ViewModel() {

    val games = MutableLiveData<MutableList<GameModel>>()

    fun getGames() {
        repository.getGames {
            games.value = it
        }
    }

    class GameViewModelFactory(
        private val repository: GameRepository
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return GameViewModel(repository) as T
        }
    }
}