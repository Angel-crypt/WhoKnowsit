package com.example.whoknowsit.core

import android.content.Context
import com.example.whoknowsit.data.LocalQuestionDataSource
import com.example.whoknowsit.domain.QuestionManager
import com.example.whoknowsit.domain.SaveManager
import com.example.whoknowsit.domain.ScoreManager
import com.example.whoknowsit.domain.SoundManager
import kotlinx.coroutines.flow.firstOrNull

/**
 * Controlador principal de la lógica del juego.
 * Gestiona el estado, la puntuación, y el flujo entre preguntas.
 */
class GameController(private val context: Context) {

    private val localDataSource = LocalQuestionDataSource(context)
    private val questionManager = QuestionManager(localDataSource)
    private val scoreManager = ScoreManager()
    val soundManager = SoundManager(context)
    val saveManager = SaveManager(context)

    lateinit var gameState: GameState
        private set
    var onGameFinished: ((finalScore: Int) -> Unit)? = null

    /**
     * Inicia una nueva partida con la configuración dada.
     * @param config Configuración seleccionada por el usuario.
     */
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

    /**
     * Carga una partida guardada previamente.
     * @return true si se cargó correctamente, false si no había partida guardada.
     */
    suspend fun loadSavedGame(): Boolean {
        saveManager.loadGameState.firstOrNull()?.let {
            gameState = it.copy(
                gameConfig = it.gameConfig.copy(gameMode = com.example.whoknowsit.core.enums.GameMode.CONTINUE)
            )
            scoreManager.setScore(it.score)
            return true
        }
        return false
    }

    /**
     * Procesa la respuesta seleccionada por el usuario.
     * @param selectedOptionIndex Índice de la opción elegida.
     * @param context Contexto para lanzar actividades (Feedback).
     */
    suspend fun handleAnswer(selectedOptionIndex: Int, context: Context) {
        gameState.currentQuestion?.let { question ->
            if (gameState.isFinished) return

            val isCorrect = question.correctAnswerIndex == selectedOptionIndex
            val multiplier = question.difficulty.multiplier
            var points = 0

            if (isCorrect) {
                points = 10 * multiplier
                scoreManager.addPoints(points)
                soundManager.playCorrect()
            } else {
                if (scoreManager.score > 0){
                    points = -5 * multiplier
                    scoreManager.subtractPoints(points)
                }
                soundManager.playWrong()
            }

            val intent = android.content.Intent(context, com.example.whoknowsit.ui.FeedbackActivity::class.java).apply {
                putExtra("IS_CORRECT", isCorrect)
                val correctAnswerText = question.options[question.correctAnswerIndex]
                putExtra("CORRECT_ANSWER", correctAnswerText)
                putExtra("POINTS_EARNED", points)
            }
            context.startActivity(intent)
            if (context is android.app.Activity) {
                context.finish()
            }

            gameState = gameState.copy(score = scoreManager.score).nextQuestion()
            if (gameState.isFinished) handleGameFinished()
        }
    }

    private suspend fun handleGameFinished() {
        val finalScore = scoreManager.score
        onGameFinished?.invoke(finalScore)

        val passingScore = gameState.questions.size * 10 * 0.5
        gameState.isVictory = finalScore >= passingScore

        if (gameState.gameConfig.gameMode == com.example.whoknowsit.core.enums.GameMode.CONTINUE) {
            saveManager.clearSavedGame()
        }
    }
}
