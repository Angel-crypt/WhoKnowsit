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
    private val currentGameState = GameState()

}