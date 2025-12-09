package com.example.whoknowsit.core

import android.content.Context
import com.example.whoknowsit.data.LocalQuestionDataSource
import com.example.whoknowsit.domain.QuestionManager
import com.example.whoknowsit.domain.SaveManager
import com.example.whoknowsit.domain.ScoreManager
import com.example.whoknowsit.domain.SoundManager
import kotlinx.coroutines.flow.firstOrNull

class GameController(private val context: Context) {

    private val localDataSource = LocalQuestionDataSource(context)
    private val questionManager = QuestionManager(localDataSource)
    private val scoreManager = ScoreManager()
    val soundManager = SoundManager(context)
    val saveManager = SaveManager(context)

    lateinit var gameState: GameState
        private set
    var onGameFinished: ((finalScore: Int) -> Unit)? = null

    fun startNewGame(config: GameConfig) {
        scoreManager.reset()
        gameState = GameState(
            gameConfig = config,
            questions = questionManager.getQuestionsForCategory(
                config.category,
                config.difficulty,
                config.totalQuestions
            ),
            score = scoreManager.score
        )
    }

    suspend fun loadSavedGame(): Boolean {
        saveManager.loadGameState.firstOrNull()?.let {
            gameState = it
            scoreManager.setScore(it.score)
            return true
        }
        return false
    }

    fun handleAnswer(selectedOptionIndex: Int, context: Context) {
        gameState.currentQuestion?.let { question ->
            if (gameState.isFinished) return

            val isCorrect = question.correctAnswerIndex == selectedOptionIndex

            if (isCorrect) {
                scoreManager.addPoints(10 * question.difficulty.multiplier)
                soundManager.playCorrect()
            } else {
                if (scoreManager.score > 0){
                    scoreManager.subtractPoints(-5 * question.difficulty.multiplier)
                }
                soundManager.playWrong()
            }

            val intent = android.content.Intent(context, com.example.whoknowsit.ui.FeedbackActivity::class.java).apply {
                putExtra("IS_CORRECT", isCorrect)
                val correctAnswerText = question.options[question.correctAnswerIndex]
                putExtra("CORRECT_ANSWER", correctAnswerText)
            }
            context.startActivity(intent)
            if (context is android.app.Activity) {
                context.finish()
            }

            gameState = gameState.copy(score = scoreManager.score).nextQuestion()
            if (gameState.isFinished) handleGameFinished()
        }
    }

    private fun handleGameFinished() {
        val finalScore = scoreManager.score
        onGameFinished?.invoke(finalScore)

        val passingScore = gameState.questions.size * 10 * 0.5
        gameState.isVictory = finalScore >= passingScore
        if (gameState.isVictory) soundManager.playWin() else soundManager.playLose()
    }
}
