package com.example.whoknowsit.core

import android.content.Context
import com.example.whoknowsit.data.LocalQuestionDataSource
import com.example.whoknowsit.domain.QuestionManager
import com.example.whoknowsit.domain.ScoreManager
import com.example.whoknowsit.domain.SoundManager

class GameController(private val context: Context) {
    private val scoreManager = ScoreManager()
    private val soundManager = SoundManager(context)
    private var currentGameState: GameState? = null

    var onGameFinished: ((finalScore: Int) -> Unit)? = null
    val localDataSource = LocalQuestionDataSource(context)
    val questionManager = QuestionManager(localDataSource)

    fun startNewGame(category: Category, difficulty: Difficulty, totalQuestions: Int) {
        scoreManager.reset()
        val questions = questionManager.getQuestionsForCategory(category, difficulty, totalQuestions)
        currentGameState = GameState(
            selectedCategory = category,
            selectedDifficulty = difficulty,
            questions = questions
        )
    }

    val state: GameState?
        get() = currentGameState

    fun handleAnswer(selectedOptionIndex: Int){
        val state = currentGameState ?: return
        val question = state.currentQuestion ?: return

        if (state.isFinished) return
        val isCorrect = (question.correctAnswerIndex == selectedOptionIndex)

        if (isCorrect) {
            val difficulty = question.difficulty
            val points = 10 * difficulty.multiplier
            scoreManager.addPoints(points)
            soundManager.playCorrect()
        } else {
            soundManager.playWrong()
        }

        val newState = state.nextQuestion()
        currentGameState = newState

        if (newState.isFinished) {
            handleGameFinished()
        }
    }

    private fun handleGameFinished() {
        val finalScore = scoreManager.score
        onGameFinished?.invoke(finalScore)

        val totalQuestions = currentGameState?.questions?.size ?: 0
        if (finalScore > (totalQuestions * 10 * 0.5)) {
            soundManager.playWin()
        } else {
            soundManager.playLose()
        }
    }
}