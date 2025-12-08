package com.example.whoknowsit.core

import android.content.Context
import com.example.whoknowsit.data.LocalQuestionDataSource
import com.example.whoknowsit.domain.QuestionManager
import com.example.whoknowsit.domain.ScoreManager
import com.example.whoknowsit.domain.SoundManager

class GameController(private val context: Context) {
    val localDataSource = LocalQuestionDataSource(context)
    val questionManager = QuestionManager(localDataSource)
    private val scoreManager = ScoreManager()
    private val soundManager = SoundManager(context)
    private var currentGameState: GameState? = null

    fun startNewGame(category: Category, difficulty: Difficulty, totalQuestions: Int) {
        scoreManager.reset()
        val questions = questionManager.getQuestionsForCategory(category, difficulty, totalQuestions)
        currentGameState = GameState(
            selectedCategory = category,
            selectedDifficulty = difficulty,
            questions = questions
        )
    }

    fun handleAnswer(selectedOptionIndex: Int){
        val state = currentGameState ?: return
        val question = state.currentQuestion ?: return

        val isCorrect = (question.correctAnswerIndex == selectedOptionIndex)

        if (isCorrect) {
            val difficulty = question.difficulty
            val points = 10 * difficulty.multiplier
            scoreManager.addPoints(points)
            soundManager.playCorrect()
        } else {
            soundManager.playWrong()
        }

        currentGameState = state.nextQuestion(isCorrect)
    }
}