package com.example.whoknowsit.core

data class GameConfig (
    val totalQuestions: Int,
    val selectedCategory: Category,
    val difficultyMode: Difficulty,
    val gameMode: GameMode
)  { init {
    require(totalQuestions > 0) { "El número de preguntas debe ser mayor que cero." }
}}