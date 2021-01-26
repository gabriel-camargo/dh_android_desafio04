package com.gabrielcamargo.firebasechallenge.games.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gabrielcamargo.firebasechallenge.R
import com.gabrielcamargo.firebasechallenge.games.model.GameModel

class GameViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
    private val imgViewGame: ImageView = view.findViewById(R.id.imgGame_layoutCardGame)
    private val txtName: TextView = view.findViewById(R.id.txtName_layoutCardGame)
    private val txtYear: TextView = view.findViewById(R.id.txtYear_layoutCardGame)

    fun bind(game: GameModel) {
        Glide.with(view)
            .load(game.imgUrl)
            .centerCrop()
            .into(imgViewGame)

        txtName.text = game.name
        txtYear.text = game.createdAt.toString()
    }
}