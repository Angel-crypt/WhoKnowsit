package com.example.whoknowsit.domain

class ScoreManager (initialScore: Int = 0)  {
    private var currentScore: Int = initialScore

    val score: Int
        get() = currentScore

    fun addPoints(points: Int) {
        validatePoints(points)
        currentScore += points
    }

    fun reset(){
        currentScore = 0
    }

    private fun validatePoints(points: Int) {
        require(points > 0) { "Los puntos deben ser mayores que 0." }
    }
}