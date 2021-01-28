package com.gabrielcamargo.firebasechallenge.games.repository

import android.content.Context
import android.util.Log
import com.gabrielcamargo.firebasechallenge.games.model.GameModel
import com.gabrielcamargo.firebasechallenge.games.view.GameFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class GameRepository(private val databaseReference: DatabaseReference) {

    fun getGames(callback: (games: MutableList<GameModel>) -> Unit) {
        val games = databaseReference.orderByKey()
        val singleListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val mutableList = mutableListOf<GameModel>()
                snapshot.children.forEach {
//                    Log.d(GameFragment.TAG, it.getValue<GameModel>().toString())
//                    Log.d(GameFragment.TAG, it.getValue<GameModel>()!!.name)
                    mutableList.add(it.getValue<GameModel>()!!)
                }
                callback.invoke(mutableList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(GameFragment.TAG, "ERROr" + error.message)
                callback.invoke(mutableListOf())
            }

        }

        games.addListenerForSingleValueEvent(singleListener)
//        callback.invoke(
//            mutableListOf<GameModel>(
//                GameModel(
//                    1,
//                    "Mortal Kombat X",
//                    "Jogo de luta com muito sangue",
//                    2018,
//                    "https://upload.wikimedia.org/wikipedia/en/thumb/d/d0/Mortal_Kombat_X_Cover_Art.png/220px-Mortal_Kombat_X_Cover_Art.png"
//                ),
//                GameModel(
//                    1,
//                    "A Lenda do Herói",
//                    "jogo de plataforma musical",
//                    2016,
//                    "https://magnaway.com.br/wp-content/uploads/2020/06/lenda-do-heroi-principal-1170x611.jpg"
//                ),
//                GameModel(
//                    1,
//                    "Cuphead",
//                    "Jogo de plataforma difícil",
//                    2017,
//                    "https://s1.gaming-cdn.com/images/products/2310/271x377/cuphead-cover.jpg"
//                ),
//                GameModel(
//                    1,
//                    "Mortal Kombat X",
//                    "Jogo de luta com muito sangue",
//                    2018,
//                    "https://upload.wikimedia.org/wikipedia/en/thumb/d/d0/Mortal_Kombat_X_Cover_Art.png/220px-Mortal_Kombat_X_Cover_Art.png"
//                ),
//                GameModel(
//                    1,
//                    "A Lenda do Herói",
//                    "jogo de plataforma musical",
//                    2016,
//                    "https://magnaway.com.br/wp-content/uploads/2020/06/lenda-do-heroi-principal-1170x611.jpg"
//                ),
//                GameModel(
//                    1,
//                    "Cuphead",
//                    "Jogo de plataforma difícil",
//                    2017,
//                    "https://s1.gaming-cdn.com/images/products/2310/271x377/cuphead-cover.jpg"
//                ),
//                GameModel(
//                    1,
//                    "Mortal Kombat X",
//                    "Jogo de luta com muito sangue",
//                    2018,
//                    "https://upload.wikimedia.org/wikipedia/en/thumb/d/d0/Mortal_Kombat_X_Cover_Art.png/220px-Mortal_Kombat_X_Cover_Art.png"
//                ),
//                GameModel(
//                    1,
//                    "A Lenda do Herói",
//                    "jogo de plataforma musical",
//                    2016,
//                    "https://magnaway.com.br/wp-content/uploads/2020/06/lenda-do-heroi-principal-1170x611.jpg"
//                ),
//                GameModel(
//                    1,
//                    "Cuphead",
//                    "Jogo de plataforma difícil",
//                    2017,
//                    "https://s1.gaming-cdn.com/images/products/2310/271x377/cuphead-cover.jpg"
//                )
//            )
//        )
    }
}