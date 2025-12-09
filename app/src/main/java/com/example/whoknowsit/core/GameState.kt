package com.example.whoknowsit.core

import com.example.whoknowsit.data.models.Question
import kotlinx.serialization.Serializable

@Serializable
data class GameState(
    val currentQuestionIndex: Int = 0,
    val selectedCategory: Category = Category.RANDOM,
    val selectedDifficulty: Difficulty = Difficulty.EASY,
    val questions: List<Question> = emptyList(),
    val score: Int = 0
) {

    val currentQuestion: Question?
        get() = questions.getOrNull(currentQuestionIndex)

    val isFinished: Boolean
        get() = currentQuestionIndex >= questions.size

    fun nextQuestion(): GameState =
        copy(currentQuestionIndex = currentQuestionIndex + 1)
}
