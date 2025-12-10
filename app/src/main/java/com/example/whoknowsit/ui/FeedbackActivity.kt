package com.example.whoknowsit.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.whoknowsit.R

class FeedbackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_feedback)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val resultTextView = findViewById<android.widget.TextView>(R.id.result_text_view)
        val resultMascotView = findViewById<android.widget.ImageView>(R.id.result_mascot_view)
        val correctAnswerTextView = findViewById<android.widget.TextView>(R.id.correct_answer_text_view)


        val isCorrect = intent.getBooleanExtra("IS_CORRECT", false)
        val correctAnswer = intent.getStringExtra("CORRECT_ANSWER")
        val pointsEarned = intent.getIntExtra("POINTS_EARNED", 0)

        if (isCorrect) {
            correctAnswerTextView.visibility = android.view.View.GONE
            resultTextView.text = "¡Bien hecho!"
            resultTextView.setTextColor(androidx.core.content.ContextCompat.getColor(this, R.color.white))
            resultMascotView.setImageResource(R.drawable.mascot_correct)
        } else {
            correctAnswerTextView.text = "La respuesta correcta era: $correctAnswer"
            correctAnswerTextView.visibility = android.view.View.VISIBLE
            resultTextView.text = "¡Sigue intentando!"
            resultTextView.setTextColor(androidx.core.content.ContextCompat.getColor(this, R.color.error))
            resultMascotView.setImageResource(R.drawable.mascot_wrong)
        }

        Toast.makeText(this, "$pointsEarned puntos", Toast.LENGTH_SHORT).show()

        val gameController = (application as com.example.whoknowsit.WhoKnowsItApplication).gameController

        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            if (!gameController.gameState.isFinished) {
                val intent = android.content.Intent(this, QuestionActivity::class.java)
                startActivity(intent)
            } else {
                val multiplier = gameController.gameState.questions.get(0).difficulty.multiplier
                val intent = android.content.Intent(this, com.example.whoknowsit.ui.ResultActivity::class.java).apply {
                    putExtra("IS_VICTORY", gameController.gameState.isVictory)
                    putExtra("FINAL_SCORE", gameController.gameState.score)
                    putExtra("TOTAL_SCORE", gameController.gameState.questions.size * 10 * multiplier)
                }
                startActivity(intent)
                finish()
            }
        }, 2000)
    }
}