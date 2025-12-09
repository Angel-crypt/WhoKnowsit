package com.example.whoknowsit.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.whoknowsit.R

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)

        val finalResultTextView = findViewById<android.widget.TextView>(R.id.final_result_text_view)
        val finalResultMascotView = findViewById<android.widget.ImageView>(R.id.final_result_mascot_view)
        val finalScoreTextView = findViewById<android.widget.TextView>(R.id.final_score_text_view)

        val isVictory = intent.getBooleanExtra("IS_VICTORY", false)
        val finalScore = intent.getIntExtra("FINAL_SCORE", 0)
        val totalScore = intent.getIntExtra("TOTAL_SCORE", 0)

        if (isVictory) {
            finalResultTextView.text = "¡Felicidades!"
            finalResultMascotView.setImageResource(R.drawable.mascot_victory)
        } else {
            finalResultTextView.text = "¡Lo sentimos!"
            finalResultMascotView.setImageResource(R.drawable.mascot_defeat)
        }
        finalScoreTextView.text = "Tu puntaje final fue: $finalScore/$totalScore"
    }
}