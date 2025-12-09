package com.example.whoknowsit.core

import com.example.whoknowsit.core.enums.Category
import com.example.whoknowsit.core.enums.Difficulty
import com.example.whoknowsit.core.enums.GameMode
import kotlinx.serialization.Serializable

@Serializable
data class GameConfig(
    val totalQuestions: Int,
    val category: Category = Category.RANDOM,
    val difficulty: Difficulty = Difficulty.EASY,
    val gameMode: GameMode
) {
    init {
        require(totalQuestions > 0) { "El número de preguntas debe ser mayor que cero." }
    }
}
