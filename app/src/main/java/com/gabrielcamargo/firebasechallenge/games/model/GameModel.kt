package com.gabrielcamargo.firebasechallenge.games.model

data class GameModel(
    val id: Int,
    val name: String,
    val description: String,
    val createdAt: Int,
    val imgUrl: String
)
