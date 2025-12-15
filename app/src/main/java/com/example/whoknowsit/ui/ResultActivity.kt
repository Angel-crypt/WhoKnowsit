package com.example.whoknowsit.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.whoknowsit.R
import com.google.android.material.button.MaterialButton

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)

        val finalResultTextView = findViewById<android.widget.TextView>(R.id.final_result_text_view)
        val finalResultMascotView = findViewById<android.widget.ImageView>(R.id.final_result_mascot_view)
        val finalScoreTextView = findViewById<android.widget.TextView>(R.id.final_score_text_view)
        val newGameButton = findViewById<MaterialButton>(R.id.new_game_button)

        val isVictory = intent.getBooleanExtra("IS_VICTORY", false)
        val finalScore = intent.getIntExtra("FINAL_SCORE", 0)
        val totalScore = intent.getIntExtra("TOTAL_SCORE", 0)

        val gameController = (application as com.example.whoknowsit.WhoKnowsItApplication).gameController
        if (isVictory) {
            gameController.soundManager.playWin()
            finalResultTextView.text = "¡Felicidades!"
            finalResultMascotView.setImageResource(R.drawable.mascot_victory)
        } else {
            gameController.soundManager.playLose()
            finalResultTextView.text = "¡Lo sentimos!"
            finalResultMascotView.setImageResource(R.drawable.mascot_defeat)
        }
        finalScoreTextView.text = "Tu puntaje final fue: $finalScore/$totalScore"

        newGameButton.setOnClickListener {
            val intent = android.content.Intent(this, com.example.whoknowsit.ui.GameActivity::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<android.widget.ImageButton>(R.id.back_home_button).setOnClickListener {
            val intent = android.content.Intent(this, com.example.whoknowsit.ui.MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}