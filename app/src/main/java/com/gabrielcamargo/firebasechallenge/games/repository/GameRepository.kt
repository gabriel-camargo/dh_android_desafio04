package com.gabrielcamargo.firebasechallenge.games.repository

import android.content.Context
import com.gabrielcamargo.firebasechallenge.games.model.GameModel

class GameRepository(private val context: Context) {

    fun getGames(callback: (games: MutableList<GameModel>) -> Unit) {
        callback.invoke(
            mutableListOf<GameModel>(
                GameModel(
                    1,
                    "Mortal Kombat X",
                    "Jogo de luta com muito sangue",
                    2018,
                    "https://upload.wikimedia.org/wikipedia/en/thumb/d/d0/Mortal_Kombat_X_Cover_Art.png/220px-Mortal_Kombat_X_Cover_Art.png"
                ),
                GameModel(
                    1,
                    "A Lenda do Herói",
                    "jogo de plataforma musical",
                    2016,
                    "https://magnaway.com.br/wp-content/uploads/2020/06/lenda-do-heroi-principal-1170x611.jpg"
                ),
                GameModel(
                    1,
                    "Cuphead",
                    "Jogo de plataforma difícil",
                    2017,
                    "https://s1.gaming-cdn.com/images/products/2310/271x377/cuphead-cover.jpg"
                ),
                GameModel(
                    1,
                    "Mortal Kombat X",
                    "Jogo de luta com muito sangue",
                    2018,
                    "https://upload.wikimedia.org/wikipedia/en/thumb/d/d0/Mortal_Kombat_X_Cover_Art.png/220px-Mortal_Kombat_X_Cover_Art.png"
                ),
                GameModel(
                    1,
                    "A Lenda do Herói",
                    "jogo de plataforma musical",
                    2016,
                    "https://magnaway.com.br/wp-content/uploads/2020/06/lenda-do-heroi-principal-1170x611.jpg"
                ),
                GameModel(
                    1,
                    "Cuphead",
                    "Jogo de plataforma difícil",
                    2017,
                    "https://s1.gaming-cdn.com/images/products/2310/271x377/cuphead-cover.jpg"
                ),
                GameModel(
                    1,
                    "Mortal Kombat X",
                    "Jogo de luta com muito sangue",
                    2018,
                    "https://upload.wikimedia.org/wikipedia/en/thumb/d/d0/Mortal_Kombat_X_Cover_Art.png/220px-Mortal_Kombat_X_Cover_Art.png"
                ),
                GameModel(
                    1,
                    "A Lenda do Herói",
                    "jogo de plataforma musical",
                    2016,
                    "https://magnaway.com.br/wp-content/uploads/2020/06/lenda-do-heroi-principal-1170x611.jpg"
                ),
                GameModel(
                    1,
                    "Cuphead",
                    "Jogo de plataforma difícil",
                    2017,
                    "https://s1.gaming-cdn.com/images/products/2310/271x377/cuphead-cover.jpg"
                )
            )
        )
    }
}