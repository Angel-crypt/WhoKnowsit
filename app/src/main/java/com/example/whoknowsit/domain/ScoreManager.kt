package com.example.whoknowsit.domain

/**
 * Gestiona la puntuación del juego en curso.
 */
class ScoreManager(initialScore: Int = 0) {

    var score: Int = initialScore
        private set

    /**
     * Suma puntos al marcador.
     */
    fun addPoints(points: Int) {
        require(points > 0) { "Los puntos deben ser mayores que 0." }
        score += points
    }

    /**
     * Resta puntos al marcador.
     */
    fun subtractPoints(points: Int) {
        require(points < 0) { "Los puntos deben ser menores que 0." }
        score += points
    }

    fun setScore(value: Int) {
        require(value >= 0) { "El puntaje no puede ser negativo." }
        score = value
    }

    fun reset() {
        score = 0
    }
}