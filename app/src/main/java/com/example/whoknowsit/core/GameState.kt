package com.example.whoknowsit.core

import com.example.whoknowsit.data.models.Question

data class GameState(
    val currentQuestionIndex: Int = 0,
    val selectedCategory: Category = Category.RANDOM,
    val selectedDifficulty: Difficulty = Difficulty.EASY,
    val questions: List<Question> = emptyList(),
    val score: Int = 0
) {
    val currentQuestion: Question?
        get() = if (currentQuestionIndex in questions.indices) questions[currentQuestionIndex] else null

    val isFinished: Boolean
        get() = currentQuestionIndex >= questions.size

    fun nextQuestion(correct: Boolean): GameState {
        return this.copy(
            currentQuestionIndex = this.currentQuestionIndex + 1,
            score = if (correct) this.score + 1 else this.score
        )
    }
}
