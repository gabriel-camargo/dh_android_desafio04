package com.gabrielcamargo.firebasechallenge.games.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gabrielcamargo.firebasechallenge.R
import com.gabrielcamargo.firebasechallenge.games.model.GameModel

class GameAdapter(
    private val dataSet: List<GameModel>,
    private val clickListener: (GameModel) -> Unit
): RecyclerView.Adapter<GameViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {

        val view =   LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_game, parent, false)

        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(dataSet[position])
        holder.itemView.setOnClickListener{clickListener(dataSet[position])}
    }

    override fun getItemCount() = dataSet.size
}