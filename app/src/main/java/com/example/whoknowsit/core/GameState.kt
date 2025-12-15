package com.example.whoknowsit.core

import com.example.whoknowsit.data.models.Question
import kotlinx.serialization.Serializable

/**
 * Estado actual del juego.
 *
 * @property currentQuestionIndex Índice de la pregunta actual.
 * @property gameConfig Configuración con la que se inició el juego.
 * @property questions Lista de preguntas de la partida.
 * @property score Puntuación actual del jugador.
 * @property isVictory Indica si el jugador ha ganado la partida.
 */
@Serializable
data class GameState(
    val currentQuestionIndex: Int = 0,
    val gameConfig: GameConfig,
    val questions: List<Question> = emptyList(),
    val score: Int = 0,
    var isVictory: Boolean = false
) {

    val currentQuestion: Question?
        get() = questions.getOrNull(currentQuestionIndex)

    val isFinished: Boolean
        get() = currentQuestionIndex >= questions.size

    fun nextQuestion(): GameState =
        copy(currentQuestionIndex = currentQuestionIndex + 1)
}
