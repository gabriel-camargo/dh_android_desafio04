package com.gabrielcamargo.firebasechallenge.games.repository

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
    }
}